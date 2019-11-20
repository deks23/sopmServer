package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sopmproject.sopmserver.model.entity.Option;
import pl.sopmproject.sopmserver.model.entity.OptionResult;
import pl.sopmproject.sopmserver.model.entity.Survey;

import java.util.List;

public interface OptionResultRepository extends JpaRepository<OptionResult, Long> {
    public List<OptionResult> findByOption(Option option);
    public List<OptionResult> findByVote(Survey survey);

}
