package pl.sopmproject.sopmserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sopmproject.sopmserver.exception.OptionNotFoundException;
import pl.sopmproject.sopmserver.model.entity.Category;
import pl.sopmproject.sopmserver.model.entity.Option;
import pl.sopmproject.sopmserver.model.entity.User;
import pl.sopmproject.sopmserver.model.entity.Survey;
import pl.sopmproject.sopmserver.model.response.AddSurveyResponse;
import pl.sopmproject.sopmserver.model.response.GetSurveysResponse;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.repository.CategoryRepository;
import pl.sopmproject.sopmserver.repository.OptionRepository;
import pl.sopmproject.sopmserver.repository.UserRepository;
import pl.sopmproject.sopmserver.repository.SurveyRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    public Option findOption(int optionId) throws OptionNotFoundException {
        Optional<Option> option = optionRepository.findById(optionId);
        if(!option.isPresent()){
            throw new OptionNotFoundException();
        }else{
            return option.get();
        }
    }
}
