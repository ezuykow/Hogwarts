package ru.hogwarts.school.services;

import ru.hogwarts.school.models.Student;

import java.util.Collection;

public interface StudentService {

    public Collection<Student> getAllStudents();

    public Student createStudent(Student student);

    public Student getStudentById(long id);

    public Student editStudent(Student student);

    public Student deleteStudentById(long id);
}
