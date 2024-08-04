package com.example.demo.service;

import com.example.demo.exception.TeacherNotFoundException;
import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
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
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void testGetAllTeachers() {
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        List<Teacher> teachers = Arrays.asList(teacher1, teacher2);

        when(teacherRepository.findAll()).thenReturn(teachers);

        List<Teacher> result = teacherService.getAllTeachers();
        assertEquals(2, result.size());
    }

    @Test
    void testGetTeacherById() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Optional<Teacher> result = teacherService.getTeacherById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testAddTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("Test Teacher");

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher result = teacherService.addTeacher(teacher);
        assertEquals("Test Teacher", result.getName());
    }

    @Test
    void testUpdateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Updated Teacher");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setName("Updated Teacher");

        Teacher result = teacherService.updateTeacher(1L, updatedTeacher);
        assertEquals("Updated Teacher", result.getName());
    }

    @Test
    void testUpdateTeacherNotFound() {
        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setName("Updated Teacher");

        when(teacherRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class, () -> {
            teacherService.updateTeacher(999L, updatedTeacher);
        });
    }

    @Test
    void testDeleteTeacher() {
        doNothing().when(teacherRepository).deleteById(1L);

        teacherService.deleteTeacher(1L);

        verify(teacherRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCountTeachers() {
        when(teacherRepository.count()).thenReturn(10L);

        long result = teacherService.countTeachers();
        assertEquals(10L, result);
    }

    @Test
    void testGetTeachersByGroupIdAndCourseId() {
        Teacher teacher = new Teacher();
        List<Teacher> teachers = Arrays.asList(teacher);

        when(teacherRepository.findTeachersByGroupIdAndCourseId(1L, 1L)).thenReturn(teachers);

        List<Teacher> result = teacherService.getTeachersByGroupIdAndCourseId(1L, 1L);
        assertEquals(1, result.size());
    }

    @Test
    void testGetTeachersByGroupNameAndCourseName() {
        String groupName = "Group A";
        String courseName = "Math";
        List<Teacher> expectedTeachers = Arrays.asList(new Teacher(), new Teacher());

        when(teacherRepository.findTeachersByGroupNameAndCourseName(groupName, courseName)).thenReturn(expectedTeachers);

        List<Teacher> actualTeachers = teacherService.getTeachersByGroupNameAndCourseName(groupName, courseName);
        assertEquals(expectedTeachers, actualTeachers);
    }
}