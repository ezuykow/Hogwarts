package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.entities.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByColorIgnoreCase(String color);

    List<Faculty> findByNameIgnoreCase(String name);
}
