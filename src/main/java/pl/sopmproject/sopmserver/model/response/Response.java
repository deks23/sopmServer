package pl.sopmproject.sopmserver.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private boolean status;
    private String responseMessage;

    public Response(Boolean status) {
        this.status = status;
        this.responseMessage = "";
    }
}
