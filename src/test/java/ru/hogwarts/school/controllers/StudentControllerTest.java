package ru.hogwarts.school.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.hogwarts.school.entities.Avatar;
import ru.hogwarts.school.entities.Faculty;
import ru.hogwarts.school.entities.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.FacultyService;
import ru.hogwarts.school.services.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class StudentControllerTest {

    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private FacultyService facultyService;

    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;

    @InjectMocks
    private StudentController studentController;
    @InjectMocks
    private FacultyController facultyController;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private List<Student> testStudentList;
    private Student testStudent1;
    private Student testStudent2;
    private Student testStudent3;

    @BeforeEach
    public void setUp() {
        final long testStudentId1 = 1;
        final long testStudentId2 = 2;
        final long testStudentId3 = 3;
        final String testStudentName1 = "Vova";
        final String testStudentName2 = "Roma";
        final String testStudentName3 = "Vanya";
        final int testStudentAge1 = 23;
        final int testStudentAge2 = 25;
        final int testStudentAge3 = 27;

        testStudent1 = new Student();
        testStudent2 = new Student();
        testStudent3 = new Student();

        testStudent1.setId(testStudentId1);
        testStudent1.setAge(testStudentAge1);
        testStudent1.setName(testStudentName1);

        testStudent2.setId(testStudentId2);
        testStudent2.setAge(testStudentAge2);
        testStudent2.setName(testStudentName2);

        testStudent3.setId(testStudentId3);
        testStudent3.setAge(testStudentAge3);
        testStudent3.setName(testStudentName3);
    }

    @Test
    public void getAllStudentsWithoutParamsShouldReturnAllStudents() throws Exception {
        testStudentList = List.of(testStudent1, testStudent2, testStudent3);

        when(studentRepository.findAll()).thenReturn(testStudentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testStudentList)));
    }

    @Test
    public void getAllStudentsWithMinParamShouldReturnAllStudentsWithAgeGreaterOrEqual() throws Exception {
        testStudentList = List.of(testStudent2, testStudent3);

        final int testMin = 24;

        when(studentRepository.findByAgeGreaterThanEqual(testMin)).thenReturn(testStudentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students")
                        .param("min", String.valueOf(testMin))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testStudentList)));
    }

    @Test
    public void getAllStudentsWithMaxParamShouldReturnAllStudentsWithAgeLessOrEqual() throws Exception {
        testStudentList = List.of(testStudent1, testStudent2);

        final int testMax = 26;

        when(studentRepository.findByAgeLessThanEqual(testMax)).thenReturn(testStudentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students")
                        .param("max", String.valueOf(testMax))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testStudentList)));
    }

    @Test
    public void getAllStudentsWithMinAndMaxParamsShouldReturnStudentsWithAgeBetween() throws Exception {
        testStudentList = List.of(testStudent2);

        final int testMin = 24;
        final int testMax = 26;

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("min", String.valueOf(testMin));
        requestParams.add("max", String.valueOf(testMax));

        when(studentRepository.findByAgeBetween(testMin, testMax)).thenReturn(testStudentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students")
                        .params(requestParams)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testStudentList)));
    }

    @Test
    public void getStudentByIdShouldReturnRightStudent() throws Exception {
        final long studentId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(testStudent1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/{id}", studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testStudent1.getId()))
                .andExpect(jsonPath("$.name").value(testStudent1.getName()))
                .andExpect(jsonPath("$.age").value(testStudent1.getAge()));
    }

    @Test
    public void getStudentFacultyShouldReturnFacultyOfStudent() throws Exception {
        Faculty testFaculty = new Faculty();
        testFaculty.setId(1);
        testFaculty.setName("testFaculty");
        testFaculty.setColor("red");
        testFaculty.setStudents(Set.of(testStudent1));

        testStudent1.setFaculty(testFaculty);
        final long studentId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(testStudent1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/{id}/faculty", studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testFaculty.getId()))
                .andExpect(jsonPath("$.name").value(testFaculty.getName()))
                .andExpect(jsonPath("$.color").value(testFaculty.getColor()));
    }

    @Test
    public void getStudentsAvatarShouldReturnAvatar() throws Exception {
        Avatar testAvatar = new Avatar();
        testAvatar.setStudent(testStudent1);
        testAvatar.setMediaType(MediaType.IMAGE_JPEG.toString());

        final long studentId = 1L;

        when(avatarRepository.findAvatarByStudentId(studentId)).thenReturn(Optional.of(testAvatar));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/{id}/avatar_from_db", studentId)
                        .accept(testAvatar.getMediaType()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG.toString()));
    }

    @Test
    public void createStudentShouldReturnCreatedStudent() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", testStudent1.getName());
        studentObject.put("age", testStudent1.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(testStudent1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testStudent1.getId()))
                .andExpect(jsonPath("$.name").value(testStudent1.getName()))
                .andExpect(jsonPath("$.age").value(testStudent1.getAge()));
    }

    @Test
    public void editStudentShouldReturnEditedStudent() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", 1);
        studentObject.put("name", "Igor");
        studentObject.put("age", 30);

        Student editedStudent = new Student();
        editedStudent.setId(1);
        editedStudent.setName("Igor");
        editedStudent.setAge(30);

        when(studentRepository.existsById(editedStudent.getId())).thenReturn(true);
        when(studentRepository.save(any(Student.class))).thenReturn(editedStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(editedStudent.getId()))
                .andExpect(jsonPath("$.name").value(editedStudent.getName()))
                .andExpect(jsonPath("$.age").value(editedStudent.getAge()));
    }

    @Test
    public void deleteStudentShouldReturnStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/students/{id}", testStudent1.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}