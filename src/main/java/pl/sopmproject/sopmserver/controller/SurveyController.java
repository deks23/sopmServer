package pl.sopmproject.sopmserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sopmproject.sopmserver.model.request.CreateNewSurveyRequest;
import pl.sopmproject.sopmserver.model.request.GetSurveysFromNeighborhoodRequest;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.model.util.Constants;
import pl.sopmproject.sopmserver.repository.SurveyRepository;
import pl.sopmproject.sopmserver.service.CategoryService;
import pl.sopmproject.sopmserver.service.SurveyService;
import pl.sopmproject.sopmserver.util.DateConverter;

import java.util.LinkedHashMap;
import java.util.Map;


//TOKEN odbierany z headera z klucza "jwt"

@RestController
public class SurveyController {
    private static final String GET_ALL_ACTIVE_SURVEYS = "/survey/getAll";
    private static final String GET_ALL_CATEGORIES = "/survey/getCategories";
    private static final String ADD_NEW_SURVEY = "/survey/addNew";
    private static final String GET_MOST_POPULAR = "/survey/getMostPopular";
    private static final String GET_SURVEY_BY_ID = "/survey/get/{surveyId}";
    private static final String GET_FROM_NEIGHBORHOOD = "/survey/getFromNeighborhood";
    private static final String GET_USER_SURVEYS = "/survey/getUserSurveys";
    private static final String GET_SURVEYS_USER_DIDNT_ANSWERED = "/survey/getNotAnswered";
    private static final String GET_SURVEYS_USER_ANSWERED = "/survey/getAnswered";


    @Autowired
    private SurveyService surveyService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SurveyRepository surveyRepository;

    @RequestMapping(value = ADD_NEW_SURVEY)
    @PostMapping
    public ResponseEntity addNew(
            @RequestHeader Map<String, String> headers,
            @RequestBody CreateNewSurveyRequest request
    ) {
        if (!headers.containsKey(Constants.JWT)) {
            Response response = Response.builder().status(false).build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Long milis = (Long)((LinkedHashMap) request.getFinishTime()).get("iLocalMillis");

        Response response = surveyService.addNewSurvey(
                headers.get(Constants.JWT),
                request.getQuestion(),
                request.getAnswerOptions(),
                request.getCategory(),
                request.getLongitude(),
                request.getLatitude(),
                DateConverter.milisToLocalDateTime(milis)
        );
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @RequestMapping(value = GET_ALL_CATEGORIES)
    @GetMapping
    public ResponseEntity getCategories(){
        Response response = categoryService.getCategories();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @RequestMapping(value = GET_ALL_ACTIVE_SURVEYS)
    @GetMapping
    public ResponseEntity getAll(){
        Response response = surveyService.getAllActiveSurveys();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @RequestMapping(value = GET_MOST_POPULAR)
    @GetMapping
    public ResponseEntity getMostPopular(){
        Response response = surveyService.getMostPopularSurveys();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @RequestMapping(value = GET_SURVEY_BY_ID)
    @GetMapping
    public ResponseEntity getSurveyById(@PathVariable(value = "surveyId") Long surveyId){
        Response response = surveyService.getSurveyById(surveyId);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @RequestMapping(value = GET_FROM_NEIGHBORHOOD)
    @PostMapping
    public ResponseEntity getSurveyById(@RequestBody GetSurveysFromNeighborhoodRequest request) {
        Response response = surveyService.getSurveysFromNeighborhood(
                request.getRadius(),
                request.getLongitude(),
                request.getLatitude());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @RequestMapping(value = GET_USER_SURVEYS)
    @GetMapping
    public ResponseEntity getUSerSurveys(@RequestHeader Map<String, String> headers){
        if (!headers.containsKey(Constants.JWT)) {
            Response response = Response.builder().status(false).build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        Response response = surveyService.getUserSurveys(headers.get(Constants.JWT));
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @RequestMapping(value = GET_SURVEYS_USER_DIDNT_ANSWERED)
    @GetMapping
    public ResponseEntity getSurveysUserDidntAnswered(@RequestHeader Map<String, String> headers){
        if (!headers.containsKey(Constants.JWT)) {
            Response response = Response.builder().status(false).build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        Response response = surveyService.getSurveysInWhichUserDidntTookPart(headers.get(Constants.JWT));
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @RequestMapping(value = GET_SURVEYS_USER_ANSWERED)
    @GetMapping
    public ResponseEntity getSurveysUserAnswered(@RequestHeader Map<String, String> headers){
        if (!headers.containsKey(Constants.JWT)) {
            Response response = Response.builder().status(false).build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        Response response = surveyService.getSurveysInWhichUserTookPart(headers.get(Constants.JWT));
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

}
