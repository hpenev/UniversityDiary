package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.exception.TeacherNotFoundException;
import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Test
    void testCountTeachers() throws Exception {
        when(teacherService.countTeachers()).thenReturn(10L);

        mockMvc.perform(get("/api/teachers/count")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(10L));
    }

    @Test
    void testGetAllTeachers() throws Exception {
        when(teacherService.getAllTeachers()).thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/api/teachers")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetTeacherById() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("John Doe");

        when(teacherService.getTeacherById(1L)).thenReturn(Optional.of(teacher));

        mockMvc.perform(get("/api/teachers/1")
                .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testAddTeacher() throws Exception {
        String teacherJson = "{\"name\": \"John Doe\"}";

        when(teacherService.addTeacher(any(Teacher.class))).thenAnswer(invocation -> {
            Teacher teacher = invocation.getArgument(0);
            teacher.setId(1L);
            return teacher;
        });

        mockMvc.perform(post("/api/teachers")
                        .with(httpBasic("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(teacherJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testUpdateTeacher() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("John Doe");

        when(teacherService.updateTeacher(eq(1L), any(Teacher.class))).thenReturn(teacher);

        String updatedTeacherJson = "{\"name\": \"John Doe\"}";

        mockMvc.perform(put("/api/teachers/1")
                        .with(httpBasic("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTeacherJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testUpdateTeacherNotFound() throws Exception {
        String updatedTeacherJson = "{\"name\": \"Jane Doe\"}";

        when(teacherService.updateTeacher(eq(999L), any(Teacher.class)))
                .thenThrow(new TeacherNotFoundException("Teacher not found with id: 999"));

        mockMvc.perform(put("/api/teachers/999")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTeacherJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteTeacher() throws Exception {
        doNothing().when(teacherService).deleteTeacher(1L);

        mockMvc.perform(delete("/api/teachers/1")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isNoContent());
    }
}