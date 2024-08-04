package com.example.demo.service;

import com.example.demo.exception.TeacherNotFoundException;
import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing teachers.
 */
@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * Retrieves all teachers.
     *
     * @return a list of all teachers
     */
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    /**
     * Retrieves a teacher by their ID.
     *
     * @param id the ID of the teacher
     * @return an Optional containing the teacher if found, or empty if not found
     */
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    /**
     * Adds a new teacher.
     *
     * @param teacher the teacher to add
     * @return the added teacher
     */
    @Transactional
    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    /**
     * Updates an existing teacher.
     *
     * @param id the ID of the teacher to update
     * @param updatedTeacher the new details of the teacher
     * @return the updated teacher
     */
    @Transactional
    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        Optional<Teacher> existingTeacherOpt = teacherRepository.findById(id);
        if (existingTeacherOpt.isPresent()) {
            Teacher existingTeacher = existingTeacherOpt.get();
            existingTeacher.setName(updatedTeacher.getName());
            // Update other fields as necessary
            return teacherRepository.save(existingTeacher);
        } else {
            throw new TeacherNotFoundException("Teacher not found with id: " + id);
        }
    }

    /**
     * Deletes a teacher by their ID.
     *
     * @param id the ID of the teacher to delete
     */
    @Transactional
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    /**
     * Counts the total number of teachers.
     *
     * @return the total number of teachers
     */
    public long countTeachers() {
        return teacherRepository.count();
    }

    /**
     * Retrieves teachers by their group and course ID.
     *
     * @param groupId the ID of the group
     * @param courseId the ID of the course
     * @return a list of teachers in the specified group and course
     */
    public List<Teacher> getTeachersByGroupIdAndCourseId(Long groupId, Long courseId) {
        return teacherRepository.findTeachersByGroupIdAndCourseId(groupId, courseId);
    }

    public List<Teacher> getTeachersByGroupNameAndCourseName(String groupName, String courseName) {
        return teacherRepository.findTeachersByGroupNameAndCourseName(groupName, courseName);
    }
}