package pl.sopmproject.sopmserver.model.entity;


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
    private Vote vote;
    private String value;
    private LocalDateTime createDate;
    @OneToOne(mappedBy = "option")
    private OptionResult result;
}
