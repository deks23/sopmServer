package pl.sopmproject.sopmserver.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User owner;
    private String question;
    private LocalDateTime finishTime;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    private double longitude;
    private double latitude;
    private long counter;
    private LocalDateTime createDate;
    @OneToMany
    private List<Option>options;
    @OneToMany
    private List<Vote>answers;


}
