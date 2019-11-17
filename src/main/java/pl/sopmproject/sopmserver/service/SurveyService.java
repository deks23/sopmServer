package pl.sopmproject.sopmserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sopmproject.sopmserver.exception.JwtParseException;
import pl.sopmproject.sopmserver.exception.SurveyNotFoundException;
import pl.sopmproject.sopmserver.model.entity.Category;
import pl.sopmproject.sopmserver.model.entity.Option;
import pl.sopmproject.sopmserver.model.entity.Survey;
import pl.sopmproject.sopmserver.model.entity.User;
import pl.sopmproject.sopmserver.model.response.AddSurveyResponse;
import pl.sopmproject.sopmserver.model.response.GetSurveysResponse;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.repository.CategoryRepository;
import pl.sopmproject.sopmserver.repository.OptionRepository;
import pl.sopmproject.sopmserver.repository.SurveyRepository;
import pl.sopmproject.sopmserver.repository.UserRepository;

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

    @Transactional
    public Response addNewSurvey(
            String jwt,
            String question,
            List<String> answerOptions,
            long category,
            double latitude,
            double longitude,
            LocalDateTime finishTime) {
        User user = null;
        try {
            user = userService.getUser(jwt);
        } catch (JwtParseException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }

        List<Option> optionList = new ArrayList();
        LocalDateTime now = LocalDateTime.now();
        Category categoryEntity = categoryRepository.getOne(category);
        Survey survey = createSurvey(question, latitude, longitude, finishTime, user, optionList, now, categoryEntity);
        fulfilOptionList(answerOptions, optionList, now, survey);
        persistData(optionList, survey);
        return AddSurveyResponse.addSurveyResponseBuilder().status(true).httpStatus(HttpStatus.OK).build();
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

    public Response getUserSurveys(String jwt) {
        User user = null;
        try {
            user = userService.getUser(jwt);
        } catch (JwtParseException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }
        List<Survey> surveyList = surveyRepository.findByOwner(user);
        return generateResponse(surveyList);
    }

    public Response getSurveysWhereUserVoted(String jwt) {
        User user = null;
        try {
            user = userService.getUser(jwt);
        } catch (JwtParseException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }
        List<Survey> surveyList = surveyRepository.getSurveysWhereUserVoted(user.getId());
        return generateResponse(surveyList);
    }

    private void persistData(List<Option> optionList, Survey survey) {
        optionRepository.saveAll(optionList);
        surveyRepository.save(survey);
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
            List<Option> optionList, LocalDateTime now, Category categoryEntity
    ) {
        return Survey.builder()
                .owner(user)
                .question(question)
                .finishTime(finishTime)
                .longitude(longitude)
                .latitude(latitude)
                .counter(0L)
                .options(optionList)
                .createDate(now)
                .category(categoryEntity)
                .build();
    }

    private void fulfilOptionList(
            List<String> answerOptions,
            List<Option> optionList,
            LocalDateTime now,
            Survey survey
    ) {
        for (String option : answerOptions) {
            Option optionEntity = new Option();
            optionEntity.setValue(option);
            optionEntity.setSurvey(survey);
            optionEntity.setCreateDate(now);
            optionList.add(optionEntity);
        }
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
