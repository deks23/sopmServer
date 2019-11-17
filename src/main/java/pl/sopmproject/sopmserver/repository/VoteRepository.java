package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sopmproject.sopmserver.model.entity.User;
import pl.sopmproject.sopmserver.model.entity.Vote;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    public List<Vote> findByUserNot(User user);
    public List<Vote> findByUser(User user);
}
