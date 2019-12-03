package pl.sopmproject.sopmserver.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "optionResult")
public class OptionResultDTO {
    private Long id;
    private Long numberOfVotes;
}
