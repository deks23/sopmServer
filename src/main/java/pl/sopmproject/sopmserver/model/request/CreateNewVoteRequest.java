package pl.sopmproject.sopmserver.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateNewVoteRequest {
    private int optionId;
}
