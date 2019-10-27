package pl.sopmproject.sopmserver.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private boolean loggedIn;
    @OneToMany(mappedBy = "owner")
    private List<Survey> surveys;
}
