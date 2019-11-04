package pl.sopmproject.sopmserver.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "answers")
@Getter
@Setter
@Builder
public class Vote {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Option option;
    private LocalDateTime createDate;



}
