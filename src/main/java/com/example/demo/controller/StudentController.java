package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Student entities.
 * Provides endpoints for CRUD operations and other student-related actions.
 */
@RestController
@RequestMapping("/api/students")
@Tag(name = "Student", description = "Student management APIs")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Get the total number of students.
     *
     * @return the total number of students
     */
    @GetMapping("/count")
    @Operation(summary = "Get the total number of students")
    public ResponseEntity<Long> countStudents() {
        return ResponseEntity.ok(studentService.countStudents());
    }

    /**
     * Add a new student.
     *
     * @param student the student to add
     * @return the added student
     */
    @PostMapping
    @Operation(summary = "Add a new student")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    /**
     * Update an existing student.
     *
     * @param id the ID of the student to update
     * @param studentDetails the updated student details
     * @return the updated student
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing student")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDetails));
    }

    /**
     * Delete a student by ID.
     *
     * @param id the ID of the student to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student by ID")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get students by course ID.
     *
     * @param courseId the ID of the course
     * @return a list of students enrolled in the specified course
     */
    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get students by course ID")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable Long courseId) {
        List<Student> students = studentService.getStudentsByCourse(courseId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/course")
    @Operation(summary = "Get students by course name")
    public ResponseEntity<List<Student>> getStudentsByCourseName(@RequestParam String name) {
        List<Student> students = studentService.getStudentsByCourseName(name);
        return ResponseEntity.ok(students);
    }

    /**
     * Get students by group.
     *
     * @param groupId the group of the students
     * @return a list of students in the specified group
     */
    @GetMapping("/group/{groupId}")
    @Operation(summary = "Get students by group ID")
    public ResponseEntity<List<Student>> getStudentsByGroupId(@PathVariable Long groupId) {
        List<Student> students = studentService.getStudentsByGroupId(groupId);
        return ResponseEntity.ok(students);
    }

    /**
     * Get students by group name.
     *
     * @param name the name of the group
     * @return a list of students in the specified group
     */
    @GetMapping("/group")
    @Operation(summary = "Get students by group name")
    public ResponseEntity<List<Student>> getStudentsByGroupName(@RequestParam String name) {
        List<Student> students = studentService.getStudentsByGroupName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping
    @Operation(summary = "Get students by age greater than or equal to a specific value and course ID")
    public ResponseEntity<List<Student>> getStudentsByAgeGreaterThanOrEqualAndCourse(
            @RequestParam("age") String ageParam,
            @RequestParam("courseId") Long courseId) {
        int age = Integer.parseInt(ageParam.split(":")[1]);
        List<Student> students = studentService.getStudentsByAgeGreaterThanOrEqualAndCourse(age, courseId);
        return ResponseEntity.ok(students);
    }
}
