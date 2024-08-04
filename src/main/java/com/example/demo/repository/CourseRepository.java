package com.example.demo.repository;

import com.example.demo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Course entities.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Retrieves the total number of courses of a given type.
     *
     * @param typeName the type of the courses to count
     * @return the total number of courses of the specified type
     */
    @Query("SELECT COUNT(c) FROM Course c WHERE c.type.name = :typeName")
    long countByTypeName(@Param("typeName") String typeName);
}