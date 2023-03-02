package ru.hogwarts.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.StudentService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
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

    @GetMapping("{id}/avatar")


    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @PostMapping(value = "{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable long studentId,
                                               @RequestParam MultipartFile avatarFile) throws IOException {
        if (avatarService.uploadStudentAvatar(studentId, avatarFile)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
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
