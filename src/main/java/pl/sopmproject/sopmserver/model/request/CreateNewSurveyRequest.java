package pl.sopmproject.sopmserver.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateNewSurveyRequest {
    private String question;
    private List<String> answerOptions;
    private long category;
    private double latitude;
    private double longitude;
    private LocalDateTime finishTime;
}
