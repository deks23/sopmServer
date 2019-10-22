package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sopmproject.sopmserver.model.entity.User;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
     Optional<User> findUserByUsername(String username);
     boolean existsUserByUsername(String username);
}
