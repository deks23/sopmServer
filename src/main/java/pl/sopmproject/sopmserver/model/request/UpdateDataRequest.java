package pl.sopmproject.sopmserver.model.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateDataRequest {
    private org.joda.time.LocalDateTime birthday;
    private String gender;
}
