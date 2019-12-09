package pl.sopmproject.sopmserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sopmproject.sopmserver.exception.JwtParseException;
import pl.sopmproject.sopmserver.exception.SurveyNotFoundException;
import pl.sopmproject.sopmserver.model.entity.*;
import pl.sopmproject.sopmserver.model.response.AddSurveyResponse;
import pl.sopmproject.sopmserver.model.response.GetSurveysResponse;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private OptionResultRepository optionResultRepository;

    @Transactional
    public Response addNewSurvey(
            String jwt,
            String question,
            List<String> answerOptions,
            long categoryId,
            double longitude,
            double latitude,
            LocalDateTime finishTime
    ) {
        User user = null;
        try {
            user = userService.getUser(jwt);
        } catch (JwtParseException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }

        LocalDateTime now = LocalDateTime.now();
        Category category = categoryRepository.getOne(categoryId);
        Survey survey = createSurvey(question, latitude, longitude, finishTime, user, now, category);
        List<Option> options = generateOptions(answerOptions, now, survey);
        List<OptionResult> optionResults = generateOptionResults(options, survey);
        persistData(options, survey, optionResults);
        return AddSurveyResponse.addSurveyResponseBuilder().status(true).httpStatus(HttpStatus.OK).build();
    }

    private List<OptionResult> generateOptionResults(List<Option> optionList, Survey survey) {
        List<OptionResult> optionResults = new ArrayList<>();
        for(Option option : optionList){
            OptionResult optionResult = new OptionResult();
            optionResult.setOption(option);
            optionResult.setVote(survey);
            optionResults.add(optionResult);
            optionResult.setNumberOfVotes(0L);
            option.setResult(optionResult);
        }
        return optionResults;
    }

    public Response getAllActiveSurveys(){
        List<Survey> surveyList = surveyRepository.getAllActiveSurveys(LocalDateTime.now());
        return generateResponse(surveyList);
    }

    public Response getMostPopularSurveys(){
        List<Survey> surveyList = surveyRepository.getAllActiveSurveysWithMostAnswers(LocalDateTime.now());
        return generateResponse(surveyList);
    }

    public Response getSurveyById(Long surveyId) {
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        List<Survey> surveyList = new ArrayList<>();
        if (!survey.isPresent()) {
            return Response.builder().status(false).httpStatus(HttpStatus.OK).build();
        } else {
            surveyList.add(survey.get());
            return GetSurveysResponse
                    .getSurveysResponseBuilder()
                    .httpStatus(HttpStatus.OK)
                    .status(true)
                    .surveyList(surveyList)
                    .build();
        }
    }

    public Response getSurveysFromNeighborhood(double radius, double longitude, double latitude){
        List<Survey> surveyList = surveyRepository.getSurveysFromNeighborhood(radius, longitude, latitude, LocalDateTime.now());
        return generateResponse(surveyList);
    }


    public Response getSurveysInWhichUserDidntTookPart(String jwt){
        User user = null;
        try {
            user = userService.getUser(jwt);
        } catch (JwtParseException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }
        List<Long> votedIdsList = getVotedSurveysIds(getVotedSurveys(user));
        List<Survey> notVotedSurveyList = getNotAnsweredSurveys(votedIdsList);
        return generateResponse(notVotedSurveyList);
    }

    private List<Survey> getNotAnsweredSurveys(List<Long> votedIdsList) {
        if (votedIdsList.isEmpty()) {
            return surveyRepository.getAllActiveSurveys(LocalDateTime.now());
        } else {
            return surveyRepository.getNotVotedSurveys(votedIdsList);
        }
    }

    private List<Long> getVotedSurveysIds(List<Survey> surveyList) {
        List<Long> votedIds = new ArrayList<>();
        for (Survey survey : surveyList){
            votedIds.add(survey.getId());
        }
        return votedIds;
    }

    public Response getSurveysInWhichUserTookPart(String jwt){
        User user = null;
        try {
            user = userService.getUser(jwt);
        } catch (JwtParseException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }
        List<Survey> surveyList = getVotedSurveys(user);
        return generateResponse(surveyList);
    }

    private List<Survey> getVotedSurveys(User user) {
        List<Vote> voteList = voteRepository.findByUser(user);
        return surveyRepository.findByAnswersInOrderByCreateDateDesc(voteList);
    }

    public Response getUserSurveys(String jwt) {
        User user = null;
        try {
            user = userService.getUser(jwt);
        } catch (JwtParseException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }
        List<Survey> surveyList = surveyRepository.findByOwnerOrderByCreateDateDesc(user);
        return generateResponse(surveyList);
    }

    private void persistData(List<Option> optionList, Survey survey, List<OptionResult> optionResults) {
        optionRepository.saveAll(optionList);
        surveyRepository.save(survey);
        optionResultRepository.saveAll(optionResults);
    }

    private Response generateResponse(List<Survey> surveyList) {
        if (surveyList.isEmpty()) {
            return Response.builder().status(false).httpStatus(HttpStatus.OK).build();
        } else {
            return GetSurveysResponse
                    .getSurveysResponseBuilder()
                    .httpStatus(HttpStatus.OK)
                    .status(true)
                    .surveyList(surveyList)
                    .build();
        }
    }

    private Survey createSurvey(
            String question,
            double latitude,
            double longitude,
            LocalDateTime finishTime,
            User user,
            LocalDateTime now,
            Category categoryEntity
    ) {
        return Survey.builder()
                .owner(user)
                .question(question)
                .finishTime(finishTime)
                .longitude(longitude)
                .latitude(latitude)
                .counter(0L)
                .createDate(now)
                .category(categoryEntity)
                .build();
    }

    private List<Option> generateOptions(
            List<String> answerOptions,
            LocalDateTime now,
            Survey survey
    ) {
        List<Option> options = new ArrayList<>();
        for (String option : answerOptions) {
            Option optionEntity = new Option();
            optionEntity.setValue(option);
            optionEntity.setSurvey(survey);
            optionEntity.setCreateDate(now);
            options.add(optionEntity);
        }
        survey.setOptions(options);
        return options;
    }

    public Survey findSurvey(int surveyId) throws SurveyNotFoundException {
        Survey survey = surveyRepository.findById(surveyId);
        if(survey == null){
            throw new SurveyNotFoundException();
        }else{
            return survey;
        }
    }

}
