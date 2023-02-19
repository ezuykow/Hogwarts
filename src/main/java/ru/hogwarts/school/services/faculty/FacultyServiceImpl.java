package ru.hogwarts.school.services.faculty;

import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.services.FacultyService;

import java.util.Collection;
import java.util.HashMap;

public class FacultyServiceImpl implements FacultyService {

    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long UId;

    @Override
    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++UId);
        faculties.put(UId, faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyById(long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        final long facultyId = faculty.getId();

        if (faculties.containsKey(facultyId)) {
            faculties.put(facultyId, faculty);
            return faculty;
        }

        return null;
    }

    @Override
    public Faculty deleteFacultyById(long id) {
        return faculties.remove(id);
    }
}
