package pl.sopmproject.sopmserver.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName(value = "option")
public class OptionDTO {
    private Long id;
    private String value;
    private OptionResultDTO result;
}
