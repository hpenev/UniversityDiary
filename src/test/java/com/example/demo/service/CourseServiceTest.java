package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void testGetAllCourses() {
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses = Arrays.asList(course1, course2);

        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAllCourses();
        assertEquals(2, result.size());
    }

    @Test
    void testGetCourseById() {
        Course course = new Course();
        course.setId(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.getCourseById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testAddCourse() {
        Course course = new Course();
        course.setName("Test Course");

        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.addCourse(course);
        assertEquals("Test Course", result.getName());
    }

    @Test
    void testUpdateCourse() {
        Course course = new Course();
        course.setId(1L);
        course.setName("Updated Course");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        Course updatedCourse = new Course();
        updatedCourse.setName("Updated Course");

        Course result = courseService.updateCourse(1L, updatedCourse);
        assertEquals("Updated Course", result.getName());
    }

    @Test
    void testDeleteCourse() {
        doNothing().when(courseRepository).deleteById(1L);

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetCoursesNumberByTypeName() {
        String typeName = "MATH";
        long expectedCount = 5L;

        when(courseRepository.countByTypeName(typeName)).thenReturn(expectedCount);

        long actualCount = courseService.getCoursesNumberByTypeName(typeName);
        assertEquals(expectedCount, actualCount);
    }

}