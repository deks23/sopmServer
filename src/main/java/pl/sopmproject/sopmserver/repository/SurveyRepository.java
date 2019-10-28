package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sopmproject.sopmserver.model.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
