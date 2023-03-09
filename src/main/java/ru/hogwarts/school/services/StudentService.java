package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entities.Faculty;
import ru.hogwarts.school.entities.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Collection<Student> getAllStudents(Integer min, Integer max) {
        logger.info("Invoked method 'getAllStudents'");
        return getAllStudentsWithAgeFilter(min, max);
    }

    public Optional<Student> getStudentById(long id) {
        logger.info("Invoked method 'getStudentById'");
        return studentRepository.findById(id);
    }

    public Optional<Faculty> getStudentsFacultyById(long id) {
        logger.info("Invoked method 'getStudentsFacultyById'");
        return getStudentById(id).map(Student::getFaculty);
    }

    public Integer getCountOfStudents() {
        logger.info("Invoked method 'getCountOfStudents'");
        return studentRepository.getCountOfStudents();
    }

    public Integer getAverageAge() {
        logger.info("Invoked method 'getAverageAge'");
        return studentRepository.getAverageAge();
    }

    public Optional<Collection<Student>> getLastFiveStudents() {
        logger.info("Invoked method 'getLastFiveStudents'");
        return Optional.of(studentRepository.getLastFiveStudents());
    }

    public Student createStudent(Student student) {
        logger.info("Invoked method 'createStudent'");
        return studentRepository.save(student);
    }

    public Optional<Student> editStudent(Student student) {
        logger.info("Invoked method 'editStudent'");
        if (studentRepository.existsById(student.getId())) {
            return Optional.of(studentRepository.save(student));
        }

        return Optional.empty();
    }

    public void deleteStudentById(long id) {
        logger.info("Invoked method 'deleteStudentById'");
        studentRepository.deleteById(id);
    }

    private Collection<Student> getAllStudentsWithAgeFilter(Integer min, Integer max) {
        if (min != null) {
            return getAllStudentsWithAgeGreaterOrBetween(min, max);
        } else {
            return getAllStudentWithAgeLessThan(max);
        }
    }

    private Collection<Student> getAllStudentsWithAgeGreaterOrBetween(Integer min, Integer max) {
        if (max != null) {
            return studentRepository.findByAgeBetween(min, max);
        }
        return studentRepository.findByAgeGreaterThanEqual(min);
    }

    private Collection<Student> getAllStudentWithAgeLessThan(Integer max) {
        if (max != null) {
            return studentRepository.findByAgeLessThanEqual(max);
        }
        return studentRepository.findAll();
    }
}
