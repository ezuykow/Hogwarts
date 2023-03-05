package ru.hogwarts.school.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.models.Avatar;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

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

    @GetMapping("/count")
    public ResponseEntity<Integer> getCountOfStudents() {
        return ResponseEntity.ok(studentService.getCountOfStudents());
    }

    @GetMapping("/avg_age")
    public ResponseEntity<Integer> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("/last")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return ResponseEntity.of(studentService.getLastFiveStudents());
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
        return ResponseEntity.of(studentService.getStudentById(id));
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getStudentsFaculty(@PathVariable("id") long id) {
        return ResponseEntity.of(studentService.getStudentsFacultyById(id));
    }

    @GetMapping("{id}/avatar_from_db")
    public ResponseEntity<byte[]> getStudentsAvatar(@PathVariable long id) {
        Optional<Avatar> avatarOptional = avatarService.findAvatar(id);

        if (avatarOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Avatar avatar = avatarOptional.get();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getFileSize());

        return ResponseEntity.status(OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("{id}/avatar_from_file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException{
        Optional<Avatar> avatarOptional = avatarService.findAvatar(id);
        if (avatarOptional.isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            Avatar avatar = avatarOptional.get();
            Path path = Path.of(avatar.getFilePath());
            try (InputStream is = Files.newInputStream(path);
                 OutputStream os = response.getOutputStream();)
            {
                response.setStatus(HttpStatus.OK.value());
                response.setContentType(avatar.getMediaType());
                response.setContentLength((int) avatar.getFileSize());
                is.transferTo(os);
            }
        }
    }

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
