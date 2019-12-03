package pl.sopmproject.sopmserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.service.ResultsService;

@RestController
public class ResultsController {

    private static final String GET_RESULTS= "/results/get/{surveyId}";

    @Autowired
    private ResultsService resultsService;

    @RequestMapping(value = GET_RESULTS)
    @GetMapping
    public ResponseEntity getSurveyResults(@PathVariable(value = "surveyId") Long surveyId){
        Response response = resultsService.getResultsById(surveyId);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

}
