package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sopmproject.sopmserver.model.User;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {
    public List<User> getAllUsers();
}
