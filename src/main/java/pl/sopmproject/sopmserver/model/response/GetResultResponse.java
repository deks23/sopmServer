package pl.sopmproject.sopmserver.model.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import pl.sopmproject.sopmserver.model.dto.OptionDTO;
import pl.sopmproject.sopmserver.model.dto.OptionResultDTO;
import pl.sopmproject.sopmserver.model.entity.Survey;

import java.util.List;

@Data
public class GetResultResponse extends Response{

    private List<OptionDTO> results;

    @Builder(builderMethodName = "GetResultResponseBuilder")

    public GetResultResponse(
            boolean status,
            String responseMessage,
            HttpStatus httpStatus,
            List<OptionDTO> results
    ) {
        super(status, responseMessage, httpStatus);
        this.results = results;
    }
}
