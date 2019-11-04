package pl.sopmproject.sopmserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonIgnore
    private String password;
    private boolean loggedIn;
    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Survey> surveys;
    private String gender;
    private int age;
}
