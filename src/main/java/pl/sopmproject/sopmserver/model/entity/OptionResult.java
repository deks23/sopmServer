package pl.sopmproject.sopmserver.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "results_history")
@Getter
@Setter
public class OptionResult {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name="survey_id")
    private Survey vote;
    @OneToOne
    @JoinColumn(name="option_id")
    private Option option;
    private Long numberOfVotes;
}
