package pl.sopmproject.sopmserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sopmproject.sopmserver.model.request.CreateNewVoteRequest;
import pl.sopmproject.sopmserver.model.response.Response;
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
        Response response = voteService.addNewVote(headers.get("jwt"),
                request.getSurveyId(),
                request.getAnswerId()
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
