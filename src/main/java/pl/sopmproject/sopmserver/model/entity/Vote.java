package pl.sopmproject.sopmserver.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User owner;
    private String question;
    private LocalDateTime finishTime;
    @ManyToOne
    private Category category;
    private double longitude;
    private double latitude;
    private long counter;
    private LocalDateTime createDate;
    @OneToMany
    private List<Option>options;
    @OneToMany
    private List<Answer>answers;


}
