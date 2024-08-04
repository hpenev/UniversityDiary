package com.example.demo.repository;

import com.example.demo.model.Course;
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
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void testCountByTypeName() {
        long count = courseRepository.countByTypeName("MATH");
        assertTrue(count > 0);
    }
}