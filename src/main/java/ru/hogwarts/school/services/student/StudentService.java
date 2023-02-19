package ru.hogwarts.school.services.student;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();
    private long UId;

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Student createStudent(Student student) {
        student.setId(++UId);
        students.put(UId, student);
        return student;
    }

    public Student getStudentById(long id) {
        return students.get(id);
    }

    public Student editStudent(Student student) {
        final long studentId = student.getId();
        if (students.containsKey(studentId)) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudentById(long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return students.values().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }
}
