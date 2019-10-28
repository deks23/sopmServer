package pl.sopmproject.sopmserver.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AddSurveyResponse extends Response {
//TODO do ustalenia jakie dane będą potrzebne
    @Builder(builderMethodName = "addSurveyResponseBuilder")
    public AddSurveyResponse(boolean status, String responseMessage, HttpStatus httpStatus) {
        super(status, responseMessage, httpStatus);
    }
}
