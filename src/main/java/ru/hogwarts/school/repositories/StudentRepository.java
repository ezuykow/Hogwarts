package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.models.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAgeBetween(Integer min, Integer max);

    List<Student> findByAgeGreaterThanEqual(Integer min);

    List<Student> findByAgeLessThanEqual(Integer max);
}
