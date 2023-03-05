package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.models.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAgeBetween(Integer min, Integer max);

    List<Student> findByAgeGreaterThanEqual(Integer min);

    List<Student> findByAgeLessThanEqual(Integer max);

    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    Integer getCountOfStudents();

    @Query(value = "SELECT avg(age) FROM student", nativeQuery = true)
    Integer getAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
