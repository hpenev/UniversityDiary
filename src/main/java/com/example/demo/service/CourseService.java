package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing courses.
 */
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Retrieves all courses.
     *
     * @return a list of all courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Retrieves a course by its ID.
     *
     * @param id the ID of the course
     * @return an Optional containing the course if found, or empty if not found
     */
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    /**
     * Adds a new course.
     *
     * @param course the course to add
     * @return the added course
     */
    @Transactional
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Updates an existing course.
     *
     * @param id the ID of the course to update
     * @param courseDetails the new details of the course
     * @return the updated course
     */
    @Transactional
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id).orElseThrow();
        course.setName(courseDetails.getName());
        course.setType(courseDetails.getType());
        return courseRepository.save(course);
    }

    /**
     * Deletes a course by its ID.
     *
     * @param id the ID of the course to delete
     */
    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    /**
     * Retrieves the total number of courses of a given type.
     *
     * @param typeName  the type of the courses to count
     * @return the total number of courses of the specified type
     */
    public long getCoursesNumberByTypeName(String typeName) {
        return courseRepository.countByTypeName(typeName);
    }
}