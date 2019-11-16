package pl.sopmproject.sopmserver.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sopmproject.sopmserver.exception.OptionNotFoundException;
import pl.sopmproject.sopmserver.model.entity.Option;
import pl.sopmproject.sopmserver.model.util.Constants;
import pl.sopmproject.sopmserver.repository.OptionRepository;

import java.util.Optional;

@Service
public class OptionService {
    private static final Logger logger = LogManager.getLogger(OptionService.class);

    @Autowired
    private OptionRepository optionRepository;

    public Option findOption(int optionId) throws OptionNotFoundException {
        Optional<Option> option = optionRepository.findById(optionId);
        if(!option.isPresent()){
            logger.warn(Constants.OPTION_ID_NOT_FOUND + optionId);
            throw new OptionNotFoundException();
        }else{
            return option.get();
        }
    }
}
