package ru.hogwarts.school.services;

import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;

import java.util.Collection;

public interface FacultyService {

    public Collection<Faculty> getAllFaculties();

    public Faculty createFaculty(Faculty faculty);

    public Faculty getFacultyById(long id);

    public Faculty editFaculty(Faculty faculty);

    public Faculty deleteFacultyById(long id);
}
