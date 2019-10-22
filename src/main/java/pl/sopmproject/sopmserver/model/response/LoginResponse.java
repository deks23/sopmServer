package pl.sopmproject.sopmserver.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends Response{
    private String userName;
    private String jwt;

    public LoginResponse(Boolean status) {
        super(status);
    }
}
