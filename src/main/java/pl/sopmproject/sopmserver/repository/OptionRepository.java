package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sopmproject.sopmserver.model.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
