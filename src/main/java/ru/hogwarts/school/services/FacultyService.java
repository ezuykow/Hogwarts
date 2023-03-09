package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entities.Faculty;
import ru.hogwarts.school.entities.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Collection<Faculty> getAllFaculties(String name, String color) {
        logger.info("Invoked method 'getAllFaculties'");
        return getAllFacultiesWithFilter(name, color);
    }

    public Optional<Faculty> getFacultyById(long id) {
        logger.info("Invoked method 'getFacultyById'");
        return facultyRepository.findById(id);
    }

    public Optional<Collection<Student>> getFacultyStudents(long id) {
        logger.info("Invoked method 'getFacultyStudents'");
        return getFacultyById(id).map(Faculty::getStudents);
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Invoked method 'createFaculty'");
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> editFaculty(Faculty faculty) {
        logger.info("Invoked method 'editFaculty'");
        if (facultyRepository.existsById(faculty.getId())) {
            return Optional.of(facultyRepository.save(faculty));
        }

        return Optional.empty();

    }

    public void deleteFacultyById(long id) {
        logger.info("Invoked method 'deleteFacultyById'");
        facultyRepository.deleteById(id);
    }

    private Collection<Faculty> getAllFacultiesWithFilter(String name, String color) {
        if (name != null) {
            return facultyRepository.findByNameIgnoreCase(name);
        }
        return getAllFacultiesByColor(color);
    }

    private Collection<Faculty> getAllFacultiesByColor(String color) {
        if (color != null) {
            return facultyRepository.findByColorIgnoreCase(color);
        }
        return facultyRepository.findAll();
    }
}
