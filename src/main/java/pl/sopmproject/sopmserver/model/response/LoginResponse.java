package pl.sopmproject.sopmserver.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends Response{
    private String username;
    private String jwt;

    public LoginResponse(Boolean status) {
        super(status);
    }
}
