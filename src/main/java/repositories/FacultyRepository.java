package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.models.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
