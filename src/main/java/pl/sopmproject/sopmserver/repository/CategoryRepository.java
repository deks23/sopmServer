package pl.sopmproject.sopmserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sopmproject.sopmserver.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
