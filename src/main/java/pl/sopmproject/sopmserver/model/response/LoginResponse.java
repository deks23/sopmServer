package pl.sopmproject.sopmserver.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LoginResponse extends Response{
    private String username;
    private String jwt;
    private boolean needData;

    @Builder(builderMethodName = "loginBuilder")
    public LoginResponse(boolean status,
                         String responseMessage,
                         HttpStatus httpStatus,
                         String username,
                         String jwt,
                         boolean needData) {
        super(status, responseMessage, httpStatus);
        this.username = username;
        this.jwt = jwt;
        this.needData = needData;
    }
}

