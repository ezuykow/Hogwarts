package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.entities.Faculty;
import ru.hogwarts.school.entities.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Collection<Student> getAllStudents(Integer min, Integer max) {
        return getAllStudentsWithAgeFilter(min, max);
    }

    public Optional<Student> getStudentById(long id) {
        return studentRepository.findById(id);
    }

    public Optional<Faculty> getStudentsFacultyById(long id) {
        return getStudentById(id).map(Student::getFaculty);
    }

    public Integer getCountOfStudents() {
        return studentRepository.getCountOfStudents();
    }

    public Integer getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public Optional<Collection<Student>> getLastFiveStudents() {
        return Optional.of(studentRepository.getLastFiveStudents());
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> editStudent(Student student) {
        if (studentRepository.existsById(student.getId())) {
            return Optional.of(studentRepository.save(student));
        }

        return Optional.empty();
    }

    public void deleteStudentById(long id) {
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
