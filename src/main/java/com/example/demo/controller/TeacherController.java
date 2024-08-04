package com.example.demo.controller;

import com.example.demo.exception.TeacherNotFoundException;
import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Teacher entities.
 * Provides endpoints for CRUD operations and other teacher-related actions.
 */
@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teacher", description = "Teacher management APIs")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * Get the total number of teachers.
     *
     * @return the total number of teachers
     */
    @GetMapping("/count")
    @Operation(summary = "Get the total number of teachers")
    public ResponseEntity<Long> countTeachers() {
        return ResponseEntity.ok(teacherService.countTeachers());
    }

    /**
     * Get all teachers.
     *
     * @return a list of all teachers
     */
    @GetMapping
    @Operation(summary = "Get all teachers")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }


    /**
     * Get a teacher by ID.
     *
     * @param id the ID of the teacher
     * @return the teacher with the specified ID, or 404 if not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a teacher by ID")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherService.getTeacherById(id);
        return teacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Add a new teacher.
     *
     * @param teacher the teacher to add
     * @return the added teacher
     */
    @PostMapping
    @Operation(summary = "Add a new teacher")
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.addTeacher(teacher));
    }

    /**
     * Update an existing teacher.
     *
     * @param id             the ID of the teacher to update
     * @param teacherDetails the updated teacher details
     * @return the updated teacher
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing teacher")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        try {
            return ResponseEntity.ok(teacherService.updateTeacher(id, teacherDetails));
        } catch (TeacherNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a teacher by ID.
     *
     * @param id the ID of the teacher to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a teacher by ID")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
