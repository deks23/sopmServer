package pl.sopmproject.sopmserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sopmproject.sopmserver.model.request.CreateNewVoteRequest;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.service.CategoryService;
import pl.sopmproject.sopmserver.service.VoteService;

import java.util.Map;


//TOKEN odbierany z headera z klucza "jwt"

@RestController
public class VoteController {
    public static final String GET_All_VOTINGS = "/vote/getAll";
    public static final String GET_ALL_CATEGORIES = "vote/getCategories";
    public static final String ADD_NEW_VOTE = "/vote/addNew";

    @Autowired
    private VoteService voteService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = ADD_NEW_VOTE)
    @PostMapping
    public ResponseEntity addNew(@RequestHeader Map<String, String> headers, @RequestBody CreateNewVoteRequest request){
        Response response = voteService.addNewVote(headers.get("jwt"),
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
