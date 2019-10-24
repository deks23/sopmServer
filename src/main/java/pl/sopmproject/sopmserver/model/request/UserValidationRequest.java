package pl.sopmproject.sopmserver.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserValidationRequest {
    private String username;
    private String password;
}
