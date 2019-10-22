package pl.sopmproject.sopmserver.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserValidationRequest {
    private String userName;
    private String password;
}
