package com.example.demo.repository;

import com.example.demo.model.Teacher;
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
@Sql(scripts = "/test-data.sql") // Populate test data
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void testFindTeachersByGroupNameAndCourseId() {
        List<Teacher> teachersGroupA = teacherRepository.findTeachersByGroupNameAndCourseId("A", 1L);
        assertFalse(teachersGroupA.isEmpty());
        assertEquals("A", teachersGroupA.get(0).getGroupName());

        List<Teacher> teachersGroupB = teacherRepository.findTeachersByGroupNameAndCourseId("B", 1L);
        assertFalse(teachersGroupB.isEmpty());
        assertEquals("B", teachersGroupB.get(0).getGroupName());
    }
}