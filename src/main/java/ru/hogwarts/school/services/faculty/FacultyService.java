package ru.hogwarts.school.services.faculty;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long UId;

    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++UId);
        faculties.put(UId, faculty);
        return faculty;
    }

    public Faculty getFacultyById(long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        final long facultyId = faculty.getId();

        if (faculties.containsKey(facultyId)) {
            faculties.put(facultyId, faculty);
            return faculty;
        }

        return null;
    }

    public Faculty deleteFacultyById(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return faculties.values().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
