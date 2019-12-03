package pl.sopmproject.sopmserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.sopmproject.sopmserver.configuration.ModelMapperConfig;
import pl.sopmproject.sopmserver.model.dto.OptionDTO;
import pl.sopmproject.sopmserver.model.dto.OptionResultDTO;
import pl.sopmproject.sopmserver.model.entity.Option;
import pl.sopmproject.sopmserver.model.entity.OptionResult;
import pl.sopmproject.sopmserver.model.entity.Survey;
import pl.sopmproject.sopmserver.model.response.GetResultResponse;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.model.util.Constants;
import pl.sopmproject.sopmserver.repository.OptionRepository;
import pl.sopmproject.sopmserver.repository.OptionResultRepository;
import pl.sopmproject.sopmserver.repository.SurveyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultsService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private OptionResultRepository optionResultRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private ModelMapperConfig modelMapperConfig;

    public Response getResultsById(Long id) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);
        if (!surveyOptional.isPresent()) {
            return Response.builder().responseMessage(Constants.SURVEY_NOT_FOUND).status(false).httpStatus(HttpStatus.NOT_FOUND).build();
        }
        List<Option> options = optionRepository.findBySurvey(surveyOptional.get());
        if(options.isEmpty()){
            GetResultResponse.GetResultResponseBuilder().httpStatus(HttpStatus.OK).results(new ArrayList<>()).status(true).build();
        }

        List<OptionDTO> resultList = prepareResultList(options);
        return GetResultResponse.GetResultResponseBuilder().httpStatus(HttpStatus.OK).results(resultList).status(true).build();
    }

    private List<OptionDTO> prepareResultList(List<Option> options) {
        return options
                    .stream()
                    .map(e -> {
                        OptionDTO option= modelMapperConfig.modelMapper().map(e, OptionDTO.class);
                        if(e.getResult() != null){
                            option.setResult(modelMapperConfig.modelMapper().map(e.getResult(), OptionResultDTO.class));
                        } else {
                            option.setResult(OptionResultDTO.builder().numberOfVotes(0L).build());
                        }
                        return option;
                    })
                    .collect(Collectors.toList());
    }
}
