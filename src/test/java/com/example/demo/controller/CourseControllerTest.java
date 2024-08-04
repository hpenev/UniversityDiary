package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.Course;
import com.example.demo.model.CourseType;
import com.example.demo.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@WebMvcTest(CourseController.class)
@Import(SecurityConfig.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCourseById() throws Exception {
        CourseType main = new CourseType(1L, "Main");
        Course course = new Course(1L, "Course 1", main, null, null);
        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));

        mockMvc.perform(get("/api/courses/1")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Course 1"))
                .andExpect(jsonPath("$.type.name").value("Main"));
    }

    @Test
    void testGetAllCourses() throws Exception {
        CourseType main = new CourseType(1L, "Main");
        CourseType secondary = new CourseType(1L, "Secondary");
        List<Course> courses = Arrays.asList(
                new Course(1L, "Course 1", main, null, null),
                new Course(2L, "Course 2", secondary, null, null));

        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/api/courses")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Course 1"))
                .andExpect(jsonPath("$[0].type.name").value("Main"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Course 2"))
                .andExpect(jsonPath("$[1].type.name").value("Secondary"));
    }

    @Test
    void testCreateCourse() throws Exception {
        CourseType main = new CourseType(1L, "Main");
        Course course = new Course(1L, "Course 1", main, null, null);
        when(courseService.addCourse(any(Course.class))).thenReturn(course);

        MockHttpServletRequestBuilder request = post("/api/courses")
                .with(httpBasic("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Course 1\", \"type\": {\"id\": 1, \"name\": \"Main\"}}");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Course 1"))
                .andExpect(jsonPath("$.type.name").value("Main"));
    }

    @Test
    void testUpdateCourse() throws Exception {
        CourseType main = new CourseType(1L, "Main");
        Course updatedCourse = new Course(1L, "Updated Course", main, null, null);
        when(courseService.updateCourse(anyLong(), any(Course.class))).thenReturn(updatedCourse);

        MockHttpServletRequestBuilder request = put("/api/courses/1")
                .with(httpBasic("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Course\", \"type\": {\"id\": 1, \"name\": \"Main\"}}");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Course"))
                .andExpect(jsonPath("$.type.name").value("Main"));
    }

    @Test
    void testDeleteCourse() throws Exception {
        doNothing().when(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/api/courses/1")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateCourseUnauthorized() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/courses")
                .with(httpBasic("user", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Course 1\", \"type\": {\"id\": 1, \"name\": \"Main\"}}");

        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetCoursesByType() throws Exception {
        when(courseService.getCoursesNumberByTypeName("Main")).thenReturn(5L);

        mockMvc.perform(get("/api/courses")
                        .with(httpBasic("user", "password"))
                        .param("type", "Main")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5L));
    }
}