package pl.sopmproject.sopmserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sopmproject.sopmserver.exception.JwtParseException;
import pl.sopmproject.sopmserver.exception.SurveyNotFoundException;
import pl.sopmproject.sopmserver.exception.OptionNotFoundException;
import pl.sopmproject.sopmserver.model.entity.Survey;
import pl.sopmproject.sopmserver.model.entity.Option;
import pl.sopmproject.sopmserver.model.entity.User;
import pl.sopmproject.sopmserver.model.entity.Vote;
import pl.sopmproject.sopmserver.model.response.CreateNewVoteResponse;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.repository.VoteRepository;

import java.time.LocalDateTime;

@Service
public class VoteService {
    @Autowired
    private UserService userService;
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private OptionService optionService;

    @Transactional
    public Response addNewVote(String jwt,
                                 int surveyId,
                                 int optionId) {
        User user = null;
        try {
            user = userService.getUser(jwt);
        } catch (JwtParseException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }

        Survey survey = null;
        try {
            survey = surveyService.findSurvey(surveyId);
        } catch (SurveyNotFoundException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }

        Option option = null;
        try {
            option = optionService.findOption(surveyId, optionId);
        } catch (OptionNotFoundException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.FORBIDDEN).build();
        }

        LocalDateTime now = LocalDateTime.now();
        Vote vote = createVote(survey, option, now, user);
        persistData(vote);
        return CreateNewVoteResponse.addVoteResponseBuilder().status(true).httpStatus(HttpStatus.OK).build();
    }

    private Vote createVote(Survey survey,
                                Option option,
                                LocalDateTime createDate,
                                User user) {
        return Vote.builder()
                .owner(user)
                .survey(survey)
                .option(option)
                .createDate(createDate)
                .build();
    }

    private void persistData(Vote vote) {
        voteRepository.save(vote);
    }

}
