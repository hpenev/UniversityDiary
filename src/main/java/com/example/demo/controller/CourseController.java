package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.service.CourseService;
import com.example.demo.service.StudentService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing Course entities.
 * Provides endpoints for CRUD operations and other course-related actions.
 */
@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course", description = "Course management APIs")
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    /**
     * Get all courses.
     *
     * @return a list of all courses
     */
    @GetMapping
    @Timed(value = "courses.getAllCourses", description = "Time taken to get all courses")
    @Operation(summary = "Get all courses")
    public List<Course> getAllCourses() {
        LOGGER.info("Get all courses");
        return courseService.getAllCourses();
    }

    /**
     * Get a course by ID.
     *
     * @param id the ID of the course
     * @return the course with the specified ID, or 404 if not found
     */
    @GetMapping("/{id}")
    @Timed(value = "courses.getCourseById", description = "Time taken to get a course")
    @Operation(summary = "Get a course by ID")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        LOGGER.info("Get course with id {}", id);
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Add a new course.
     *
     * @param course the course to add
     * @return the added course
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "courses.addCourse", description = "Time taken to add a course")
    @Operation(summary = "Add a new course")
    public ResponseEntity<Course> addCourse(@Valid @RequestBody Course course) {
        LOGGER.info("Create new course {}", course.getName());
        return ResponseEntity.ok(courseService.addCourse(course));
    }

    /**
     * Update an existing course.
     *
     * @param id the ID of the course to update
     * @param courseDetails the updated course details
     * @return the updated course
     */
    @PutMapping("/{id}")
    @Timed(value = "courses.updateCourse", description = "Time taken to update a course")
    @Operation(summary = "Update an existing course")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody Course courseDetails) {
        LOGGER.info("Update course with id: {}", id);
        return ResponseEntity.ok(courseService.updateCourse(id, courseDetails));
    }

    /**
     * Delete a course by ID.
     *
     * @param id the ID of the course to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    @Timed(value = "courses.updateCourse", description = "Time taken to delete a course")
    @Operation(summary = "Delete a course by ID")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        LOGGER.info("Delete course with id: {}", id);
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get courses by type.
     *
     * @param typeName the type of the courses
     * @return a list of courses of the specified type
     */
    @GetMapping(params = "type")
    @Timed(value = "courses.getCoursesByType", description = "Time taken to get all courses by typeName")
    @Operation(summary = "Get courses by type")
    public ResponseEntity<Long> getCoursesByType(@RequestParam("type") String typeName) {
        LOGGER.info("List courses with type: {}", typeName);
        return ResponseEntity.ok(courseService.getCoursesNumberByTypeName(typeName));
    }
}