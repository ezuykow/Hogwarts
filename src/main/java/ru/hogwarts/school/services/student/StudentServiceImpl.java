package ru.hogwarts.school.services.student;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentServiceImpl implements StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();
    private long UId;

    @Override
    public Collection<Student> getAllStudents() {
        return students.values();
    }

    @Override
    public Student createStudent(Student student) {
        student.setId(++UId);
        students.put(UId, student);
        return student;
    }

    @Override
    public Student getStudentById(long id) {
        return students.get(id);
    }

    @Override
    public Student editStudent(Student student) {
        final long studentId = student.getId();
        if (students.containsKey(studentId)) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    @Override
    public Student deleteStudentById(long id) {
        return students.remove(id);
    }
}
