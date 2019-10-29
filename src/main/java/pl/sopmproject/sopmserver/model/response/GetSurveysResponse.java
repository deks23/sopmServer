package pl.sopmproject.sopmserver.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import pl.sopmproject.sopmserver.model.entity.Survey;

import java.util.List;

@Getter
@Setter
public class GetSurveysResponse extends Response {
    private List<Survey> surveyList;

    @Builder(builderMethodName = "getSurveysResponseBuilder")
    public GetSurveysResponse(boolean status,
                              String responseMessage,
                              HttpStatus httpStatus,
                              List<Survey> surveyList) {
        super(status, responseMessage, httpStatus);
        this.surveyList = surveyList;
    }
}
