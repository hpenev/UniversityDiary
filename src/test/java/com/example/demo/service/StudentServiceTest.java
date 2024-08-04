package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
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
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testGetAllStudents() {
        Student student1 = new Student();
        Student student2 = new Student();
        List<Student> students = Arrays.asList(student1, student2);

        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();
        assertEquals(2, result.size());
    }

    @Test
    void testAddStudent() {
        Student student = new Student();
        student.setName("Test Student");

        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.addStudent(student);
        assertEquals("Test Student", result.getName());
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Updated Student");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        Student updatedStudent = new Student();
        updatedStudent.setName("Updated Student");

        Student result = studentService.updateStudent(1L, updatedStudent);
        assertEquals("Updated Student", result.getName());
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCountStudents() {
        when(studentRepository.count()).thenReturn(10L);

        long result = studentService.countStudents();
        assertEquals(10L, result);
    }

    @Test
    void testGetStudentsByCourse() {
        Student student = new Student();
        List<Student> students = Arrays.asList(student);

        when(studentRepository.findStudentsByCourseId(1L)).thenReturn(students);

        List<Student> result = studentService.getStudentsByCourse(1L);
        assertEquals(1, result.size());
    }

    @Test
    void testGetStudentsByGroupAndCourse() {
        Student student = new Student();
        List<Student> students = Arrays.asList(student);

        when(studentRepository.findStudentsByGroupIdAndCourseId(1L, 1L)).thenReturn(students);

        List<Student> result = studentService.getStudentsByGroupIdAndCourseId(1L, 1L);
        assertEquals(1, result.size());
    }

    @Test
    void testGetStudentsByAgeAndCourse() {
        Student student = new Student();
        List<Student> students = Arrays.asList(student);

        when(studentRepository.findStudentsByAgeAndCourseId(20, 1L)).thenReturn(students);

        List<Student> result = studentService.getStudentsByAgeAndCourse(20, 1L);
        assertEquals(1, result.size());
    }

    @Test
    void testGetStudentsByGroupId() {
        Long groupId = 1L;
        List<Student> expectedStudents = Arrays.asList(new Student(), new Student());

        when(studentRepository.findStudentsByGroupNameId(groupId)).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.getStudentsByGroupId(groupId);
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void testGetStudentsByCourseName() {
        String courseName = "Math";
        List<Student> expectedStudents = Arrays.asList(new Student(), new Student());

        when(studentRepository.findStudentsByCourseName(courseName)).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.getStudentsByCourseName(courseName);
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void testGetStudentsByGroupName() {
        String groupName = "Group A";
        List<Student> expectedStudents = Arrays.asList(new Student(), new Student());

        when(studentRepository.findStudentsByGroupName(groupName)).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.getStudentsByGroupName(groupName);
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void testGetStudentsByGroupNameAndCourseName() {
        String groupName = "Group A";
        String courseName = "Math";
        List<Student> expectedStudents = Arrays.asList(new Student(), new Student());

        when(studentRepository.findStudentsByGroupNameAndCourseName(groupName, courseName)).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.getStudentsByGroupNameAndCourseName(groupName, courseName);
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void testGetStudentsByAgeGreaterThanOrEqualAndCourse() {
        int age = 18;
        Long courseId = 1L;
        List<Student> expectedStudents = Arrays.asList(new Student(), new Student());

        when(studentRepository.findStudentsByAgeGreaterThanOrEqualAndCourseId(age, courseId)).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.getStudentsByAgeGreaterThanOrEqualAndCourse(age, courseId);
        assertEquals(expectedStudents, actualStudents);
    }
}