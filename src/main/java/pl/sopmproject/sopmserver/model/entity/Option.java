package pl.sopmproject.sopmserver.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "options")
@Getter
@Setter
public class Option {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name="survey_id")
    @JsonIgnore
    private Survey survey;
    private String value;
    private LocalDateTime createDate;
    @OneToOne(mappedBy = "option")
    @JsonIgnore
    private OptionResult result;

}
