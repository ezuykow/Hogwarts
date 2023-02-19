package ru.hogwarts.school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.student.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
        Student foundedStudent = studentService.getStudentById(id);

        if (foundedStudent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundedStudent);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam(name = "age") int age) {
        return ResponseEntity.ok(studentService.getStudentsByAge(age));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editedStudent = studentService.editStudent(student);

        if (editedStudent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") long id) {
        Student deletedStudent = studentService.deleteStudentById(id);

        if (deletedStudent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(deletedStudent);
    }
}
