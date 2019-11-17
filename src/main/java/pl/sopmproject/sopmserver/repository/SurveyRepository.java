package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.sopmproject.sopmserver.model.entity.Survey;
import pl.sopmproject.sopmserver.model.entity.User;

import java.time.LocalDate;
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

    @Query(value = "SELECT s.id, s.counter, s.create_date, s.finish_time, s.latitude, s.longitude, s.question, s.category_id, user_id FROM (\n" +
            "        SELECT *, (\n" +
            "        6371 * acos (\n" +
            "        cos ( radians(:latitude) ) * \n" +
            "        cos( radians( latitude ) ) * \n" +
            "        cos( radians( longitude ) - \n" +
            "        radians(:longitude) ) + \n" +
            "        sin ( radians(:latitude) ) * \n" +
            "        sin( radians( latitude ) )\n" +
            "        )\n" +
            "    ) AS distance\n" +
            "    FROM surveys\n" +
            ") AS s\n" +
            "WHERE s.distance <:radius AND s.finish_time > :now", nativeQuery = true)
    public List<Survey> getSurveysFromNeighborhood(@Param("radius")double radius, @Param("longitude")double longitude, @Param("latitude")double latitude, @Param("now") LocalDateTime now);

    public List<Survey> findByOwner(User owner);
}
