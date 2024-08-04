package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.dto.GroupCourseResponseDTO;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@WebMvcTest(GroupCourseController.class)
@Import(SecurityConfig.class)
class GroupCourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudentsAndTeachersByGroupAndCourse() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", 23, null, null),
                new Student(2L, "Jane Doe", 33, null, null));
        List<Teacher> teachers = Arrays.asList(
                new Teacher(1L, "Mr. Smith", 55, null, null),
                new Teacher(2L, "Ms. Johnson", 66, null, null));

        when(studentService.getStudentsByGroupIdAndCourseId(anyLong(), anyLong())).thenReturn(students);
        when(teacherService.getTeachersByGroupIdAndCourseId(anyLong(), anyLong())).thenReturn(teachers);

        mockMvc.perform(get("/api/course/1/group/1/byId")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students[0].id").value(1L))
                .andExpect(jsonPath("$.students[0].name").value("John Doe"))
                .andExpect(jsonPath("$.students[0].age").value(23))
                .andExpect(jsonPath("$.students[1].id").value(2L))
                .andExpect(jsonPath("$.students[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$.students[1].age").value(33))
                .andExpect(jsonPath("$.teachers[0].id").value(1L))
                .andExpect(jsonPath("$.teachers[0].name").value("Mr. Smith"))
                .andExpect(jsonPath("$.teachers[0].age").value(55))
                .andExpect(jsonPath("$.teachers[1].id").value(2L))
                .andExpect(jsonPath("$.teachers[1].name").value("Ms. Johnson"))
                .andExpect(jsonPath("$.teachers[1].age").value(66));
    }

    @Test
    void testGetStudentsAndTeachersByGroupAndCourseNames() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", 23, null, null),
                new Student(2L, "Jane Doe", 33, null, null));
        List<Teacher> teachers = Arrays.asList(
                new Teacher(1L, "Mr. Smith", 55, null, null),
                new Teacher(2L, "Ms. Johnson", 66, null, null));

        when(studentService.getStudentsByGroupNameAndCourseName(anyString(), anyString())).thenReturn(students);
        when(teacherService.getTeachersByGroupNameAndCourseName(anyString(), anyString())).thenReturn(teachers);

        mockMvc.perform(get("/api/course/CourseA/group/GroupA/byName")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students[0].id").value(1L))
                .andExpect(jsonPath("$.students[0].name").value("John Doe"))
                .andExpect(jsonPath("$.students[0].age").value(23))
                .andExpect(jsonPath("$.students[1].id").value(2L))
                .andExpect(jsonPath("$.students[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$.students[1].age").value(33))
                .andExpect(jsonPath("$.teachers[0].id").value(1L))
                .andExpect(jsonPath("$.teachers[0].name").value("Mr. Smith"))
                .andExpect(jsonPath("$.teachers[0].age").value(55))
                .andExpect(jsonPath("$.teachers[1].id").value(2L))
                .andExpect(jsonPath("$.teachers[1].name").value("Ms. Johnson"))
                .andExpect(jsonPath("$.teachers[1].age").value(66));
    }
}