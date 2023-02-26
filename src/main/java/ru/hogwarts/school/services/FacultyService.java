package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Collection<Faculty> getAllFaculties(String name, String color) {
        return getAllFacultiesWithFilter(name, color);
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> getFacultyById(long id) {
        return facultyRepository.findById(id);
    }

    public Optional<Faculty> editFaculty(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId())) {
            return Optional.of(facultyRepository.save(faculty));
        }

        return Optional.empty();

    }

    public void deleteFacultyById(long id) {
        facultyRepository.deleteById(id);
    }

    private Collection<Faculty> getAllFacultiesWithFilter(String name, String color) {
        if (name != null) {
            return facultyRepository.findByNameIgnoreCase(name);
        }
        return getAllFacultiesByColor(color);
    }

    private Collection<Faculty> getAllFacultiesByColor(String color) {
        if (color != null) {
            return facultyRepository.findByColorIgnoreCase(color);
        }
        return facultyRepository.findAll();
    }
}
