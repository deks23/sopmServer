package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sopmproject.sopmserver.model.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
