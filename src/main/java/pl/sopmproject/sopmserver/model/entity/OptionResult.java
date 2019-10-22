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
    private Vote vote;
    @OneToOne
    private Option option;
    private Long numberOfVotes;
}
