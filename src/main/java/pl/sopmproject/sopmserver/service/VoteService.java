package pl.sopmproject.sopmserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sopmproject.sopmserver.exception.JwtParseException;
import pl.sopmproject.sopmserver.model.entity.Category;
import pl.sopmproject.sopmserver.model.entity.Option;
import pl.sopmproject.sopmserver.model.entity.User;
import pl.sopmproject.sopmserver.model.entity.Vote;
import pl.sopmproject.sopmserver.model.response.AddVoteResponse;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.repository.CategoryRepository;
import pl.sopmproject.sopmserver.repository.OptionRepository;
import pl.sopmproject.sopmserver.repository.UserRepository;
import pl.sopmproject.sopmserver.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoteService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Response addNewVote(String jwt,
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
        Vote vote = createVote(question, latitude, longitude, finishTime, user, optionList, now, categoryEntity);
        fulfilOptionList(answerOptions, optionList, now, vote);
        persistData(optionList, vote);
        return AddVoteResponse.addVoteResponseBuilder().status(true).httpStatus(HttpStatus.OK).build();
    }

    private void persistData(List<Option> optionList, Vote vote) {
        optionRepository.saveAll(optionList);
        voteRepository.save(vote);
    }


    private Vote createVote(String question,
                            double latitude,
                            double longitude,
                            LocalDateTime finishTime,
                            User user,
                            List<Option> optionList, LocalDateTime now, Category categoryEntity) {
        return Vote.builder()
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

    private void fulfilOptionList(List<String> answerOptions, List<Option> optionList, LocalDateTime now, Vote vote) {
        for (String option : answerOptions) {
            Option optionEntity = new Option();
            optionEntity.setValue(option);
            optionEntity.setVote(vote);
            optionEntity.setCreateDate(now);
            optionList.add(optionEntity);
        }
    }

}
