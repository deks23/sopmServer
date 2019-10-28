package pl.sopmproject.sopmserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sopmproject.sopmserver.model.request.CreateNewSurveyRequest;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.service.CategoryService;
import pl.sopmproject.sopmserver.service.SurveyService;

import java.util.Map;


//TOKEN odbierany z headera z klucza "jwt"

@RestController
public class SurveyController {
    public static final String GET_All_VOTINGS = "/survey/getAll";
    public static final String GET_ALL_CATEGORIES = "/survey/getCategories";
    public static final String ADD_NEW_VOTE = "/survey/addNew";

    @Autowired
    private SurveyService surveyService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = ADD_NEW_VOTE)
    @PostMapping
    public ResponseEntity addNew(@RequestHeader Map<String, String> headers, @RequestBody CreateNewSurveyRequest request){
        Response response = surveyService.addNewSurvey(headers.get("jwt"),
                request.getQuestion(),
                request.getAnswerOptions(),
                request.getCategory(),
                request.getLongitude(),
                request.getLatitude(),
                request.getFinishTime());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @RequestMapping(value = GET_ALL_CATEGORIES)
    @GetMapping
    public ResponseEntity getCategories(){
        Response response = categoryService.getCategories();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

}
