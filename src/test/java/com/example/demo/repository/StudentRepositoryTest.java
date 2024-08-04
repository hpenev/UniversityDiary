package com.example.demo.repository;

import com.example.demo.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = "/test-data.sql") // populate test data
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testFindStudentsByCourseId() {
        List<Student> students = studentRepository.findStudentsByCourseId(1L);
        assertFalse(students.isEmpty());
        assertEquals(1L, students.get(0).getCourses().stream().findFirst().get().getId());
    }

    @Test
    void testFindStudentsByGroupAndCourseId() {
        List<Student> students = studentRepository.findStudentsByGroupIdAndCourseId(1L, 1L);
        assertFalse(students.isEmpty());
        assertEquals(1L, students.get(0).getGroupName());
        assertEquals(1L, students.get(0).getCourses().stream().findFirst().get().getId());
    }

    @Test
    void testFindStudentsByAgeAndCourseId() {
        List<Student> students = studentRepository.findStudentsByAgeAndCourseId(20, 1L);
        assertFalse(students.isEmpty());
        assertTrue(students.get(0).getAge() > 20);
        assertEquals(1L, students.get(0).getCourses().stream().findFirst().get().getId());
    }
}