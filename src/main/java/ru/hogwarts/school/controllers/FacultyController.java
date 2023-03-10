package ru.hogwarts.school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entities.Faculty;
import ru.hogwarts.school.entities.Student;
import ru.hogwarts.school.services.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String color) {
        return ResponseEntity.ok(facultyService.getAllFaculties(name, color));
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable("id") long id) {
        return ResponseEntity.of(facultyService.getFacultyById(id));

    }

    @GetMapping("{id}/students")
    public ResponseEntity<Collection<Student>> getFacultyStudents(@PathVariable("id") long id) {
        return ResponseEntity.of(facultyService.getFacultyStudents(id));

    }

    @GetMapping("largest_name")
    public ResponseEntity<String> getLargestName(){
        return ResponseEntity.ok(facultyService.getLargestName());
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.of(facultyService.editFaculty(faculty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable("id") long id) {
        facultyService.deleteFacultyById(id);
        return ResponseEntity.ok().build();
    }
}
