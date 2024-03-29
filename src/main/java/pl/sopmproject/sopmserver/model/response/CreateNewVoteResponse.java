package pl.sopmproject.sopmserver.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter

public class CreateNewVoteResponse extends Response {
    //TODO do ustalenia jakie dane będą potrzebne
    @Builder(builderMethodName = "addVoteResponseBuilder")
    public CreateNewVoteResponse(boolean status, String responseMessage, HttpStatus httpStatus) {
        super(status, responseMessage, httpStatus);
    }
}
