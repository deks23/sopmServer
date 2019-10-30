package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.sopmproject.sopmserver.model.entity.Survey;

import java.time.LocalDateTime;
import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    @Query(value = "SELECT s FROM Survey s WHERE s.finishTime > ?1")
    public List<Survey> getAllActiveSurveys(LocalDateTime now);

    @Query(value = "SELECT s FROM Survey s WHERE s.finishTime < ?1")
    public List<Survey> getFinishedSurveys(LocalDateTime now);

    @Query(value = "SELECT s FROM Survey s WHERE s.finishTime > ?1 ORDER BY s.counter DESC")
    public List<Survey> getAllActiveSurveysWithMostAnswers(LocalDateTime now);

    @Query(value = "SELECT s FROM Survey s WHERE s.id = ?1")
    public Survey findById(int surveyId);
}
