package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.sopmproject.sopmserver.model.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
    @Query(value = "SELECT o FROM Option o WHERE o.id = ?1 AND o.surveyId = ?2")
    public Option findById(int surveyId, int optionId);
}
