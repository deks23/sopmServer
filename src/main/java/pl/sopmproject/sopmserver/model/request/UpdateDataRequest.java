package pl.sopmproject.sopmserver.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateDataRequest {
    private LocalDateTime birthDate;
    private String gender;
}
