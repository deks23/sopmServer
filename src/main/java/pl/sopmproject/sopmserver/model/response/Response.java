package pl.sopmproject.sopmserver.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Response {
    private boolean status;
    private String responseMessage;
    @JsonIgnore
    private HttpStatus httpStatus;

}
