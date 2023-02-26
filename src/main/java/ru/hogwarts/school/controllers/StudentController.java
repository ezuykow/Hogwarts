package ru.hogwarts.school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents(@RequestParam(required = false) Integer min,
                                                              @RequestParam(required = false) Integer max) {
        return ResponseEntity.ok(studentService.getAllStudents(min, max));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
        return ResponseEntity.of(studentService.getStudentById(id));
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getStudentsFaculty(@PathVariable("id") long id) {
        return ResponseEntity.of(studentService.getStudentsFacultyById(id));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        return ResponseEntity.of(studentService.editStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok().build();
    }
}
