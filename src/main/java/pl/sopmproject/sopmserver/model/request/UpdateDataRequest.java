package pl.sopmproject.sopmserver.model.request;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;


@Getter
@Setter
public class UpdateDataRequest {
    private LocalDateTime birthday;
    private String gender;
}
