package ru.hogwarts.school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.services.faculty.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable("id") long id) {
        Faculty foundedFaculty = facultyService.getFacultyById(id);

        if (foundedFaculty == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundedFaculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(@RequestParam(name = "color") String color) {
        return ResponseEntity.ok(facultyService.getFacultiesByColor(color));
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editedFaculty = facultyService.editFaculty(faculty);

        if (editedFaculty == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editedFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable("id") long id) {
        Faculty deletedFaculty = facultyService.deleteFacultyById(id);

        if (deletedFaculty == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(deletedFaculty);
    }
}
