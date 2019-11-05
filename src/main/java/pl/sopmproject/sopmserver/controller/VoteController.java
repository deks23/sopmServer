package pl.sopmproject.sopmserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sopmproject.sopmserver.exception.SurveyEndedException;
import pl.sopmproject.sopmserver.model.request.CreateNewVoteRequest;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.model.util.Constants;
import pl.sopmproject.sopmserver.service.SurveyService;
import pl.sopmproject.sopmserver.service.VoteService;

import java.util.Map;

@RestController
public class VoteController {
    public static final String CREATE_VOTE = "/vote/create";

    @Autowired
    private VoteService voteService;

    @RequestMapping(value = CREATE_VOTE)
    @PostMapping
    public ResponseEntity addNew(@RequestHeader Map<String, String> headers, @RequestBody CreateNewVoteRequest request){
        Response response = null;
        if(!headers.containsKey(Constants.JWT)){
            response = Response.builder().status(false).responseMessage(Constants.TOKEN_NOT_PRESENT).build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        try {
           response = voteService.addNewVote(headers.get(Constants.JWT),
                    request.getOptionId());
            return ResponseEntity.status(response.getHttpStatus()).body(response);
        } catch (SurveyEndedException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
    }
}
