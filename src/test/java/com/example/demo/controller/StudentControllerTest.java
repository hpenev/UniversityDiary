package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@WebMvcTest(StudentController.class)
@Import(SecurityConfig.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student(1L, "John Doe", 33, null, null);
        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        MockHttpServletRequestBuilder request = post("/api/students")
                .with(httpBasic("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(33));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student updatedStudent = new Student(1L, "John Doe", 33, null, null);
        when(studentService.updateStudent(anyLong(), any(Student.class))).thenReturn(updatedStudent);

        MockHttpServletRequestBuilder request = put("/api/students/1")
                .with(httpBasic("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(33));
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/api/students/1")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateStudentUnauthorized() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/students")
                .with(httpBasic("user", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}");

        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetStudentsByAgeGreaterThanOrEqualAndCourse() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", 25, null, null),
                new Student(2L, "Jane Doe", 30, null, null));

        when(studentService.getStudentsByAgeGreaterThanOrEqualAndCourse(anyInt(), anyLong())).thenReturn(students);

        mockMvc.perform(get("/api/students")
                        .with(httpBasic("user", "password"))
                        .param("age", "gte:25")
                        .param("courseId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(25))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].age").value(30));
    }

    @Test
    void testGetStudentsByGroupName() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", 23, null, null),
                new Student(2L, "Jane Doe", 33, null, null));

        when(studentService.getStudentsByGroupName("GroupA")).thenReturn(students);

        mockMvc.perform(get("/api/students/group")
                        .with(httpBasic("user", "password"))
                        .param("name", "GroupA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(23))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].age").value(33));
    }

    @Test
    void testGetStudentsByGroupId() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", 23, null, null),
                new Student(2L, "Jane Doe", 33, null, null));

        when(studentService.getStudentsByGroupId(1L)).thenReturn(students);

        mockMvc.perform(get("/api/students/group/1")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(23))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].age").value(33));
    }

    @Test
    void testGetStudentsByCourseName() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", 23, null, null),
                new Student(2L, "Jane Doe", 33, null, null));

        when(studentService.getStudentsByCourseName("CourseA")).thenReturn(students);

        mockMvc.perform(get("/api/students/course")
                        .with(httpBasic("user", "password"))
                        .param("name", "CourseA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(23))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].age").value(33));
    }

    @Test
    void testGetStudentsByCourse() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", 23, null, null),
                new Student(2L, "Jane Doe", 33, null, null));

        when(studentService.getStudentsByCourse(1L)).thenReturn(students);

        mockMvc.perform(get("/api/students/course/1")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(23))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].age").value(33));
    }

    @Test
    void testCountStudents() throws Exception {
        when(studentService.countStudents()).thenReturn(10L);

        mockMvc.perform(get("/api/students/count")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(10L));
    }
}