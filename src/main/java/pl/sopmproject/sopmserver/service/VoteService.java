package pl.sopmproject.sopmserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sopmproject.sopmserver.exception.JwtParseException;
import pl.sopmproject.sopmserver.exception.OptionNotFoundException;
import pl.sopmproject.sopmserver.exception.SurveyEndedException;
import pl.sopmproject.sopmserver.model.entity.*;
import pl.sopmproject.sopmserver.model.response.CreateNewVoteResponse;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.model.util.Constants;
import pl.sopmproject.sopmserver.repository.OptionResultRepository;
import pl.sopmproject.sopmserver.repository.SurveyRepository;
import pl.sopmproject.sopmserver.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoteService {
    @Autowired
    private UserService userService;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private OptionService optionService;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private OptionResultRepository optionResultRepository;

    @Transactional
    public Response addNewVote(
            String jwt,
            int optionId
    ) throws SurveyEndedException {
        User user = null;
        try {
            user = userService.getUser(jwt);
        } catch (JwtParseException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }

        Option option = null;
        try {
            option = optionService.findOption(optionId);
        } catch (OptionNotFoundException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.NOT_FOUND).responseMessage(Constants.OPTION_NOT_FOUND).build();
        }

        LocalDateTime now = LocalDateTime.now();

        if (option.getSurvey().getFinishTime().isBefore(now)) {
            throw new SurveyEndedException();
        }
        Vote vote = createVote(option, now, user);
        Survey survey = vote.getOption().getSurvey();
        OptionResult optionResult = option.getResult();
        if (optionResult ==null){
            generateOptionResult(option, survey);
        }

        incrementCounters(survey, option);
        survey.getAnswers().add(vote);
        persistData(vote, survey);
        return CreateNewVoteResponse.addVoteResponseBuilder().status(true).httpStatus(HttpStatus.OK).build();
    }

    private void generateOptionResult(Option option, Survey survey) {
        OptionResult optionResult;
        optionResult = new OptionResult();
        optionResult.setNumberOfVotes(0L);
        optionResult.setVote(survey);
        optionResult.setOption(option);
        option.setResult(optionResult);
        optionResultRepository.save(optionResult);
    }

    private void incrementCounters(Survey survey, Option option) {
        Long votes = option.getResult().getNumberOfVotes();
        Long counter = survey.getCounter();
        survey.setCounter(++counter);
        option.getResult().setNumberOfVotes(++votes);
    }

    private Vote createVote(
            Option option,
            LocalDateTime createDate,
            User user
    ) {
        return Vote.builder()
                .user(user)
                .option(option)
                .createDate(createDate)
                .build();
    }

    private void persistData(Vote vote, Survey survey) {
        voteRepository.save(vote);
        surveyRepository.save(survey);
    }

}
