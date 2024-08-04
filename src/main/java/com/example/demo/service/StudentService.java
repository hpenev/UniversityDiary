package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing students.
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Retrieves all students.
     *
     * @return a list of all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Retrieves students by their group ID.
     *
     * @param groupId the ID of the group
     * @return a list of students in the specified group
     */
    public List<Student> getStudentsByGroupId(Long groupId) {
        return studentRepository.findStudentsByGroupNameId(groupId);
    }

    /**
     * Adds a new student.
     *
     * @param student the student to add
     * @return the added student
     */
    @Transactional
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Updates an existing student.
     *
     * @param id the ID of the student to update
     * @param studentDetails the new details of the student
     * @return the updated student
     */
    @Transactional
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id).orElseThrow();
        student.setName(studentDetails.getName());
        student.setAge(studentDetails.getAge());
        student.setGroupName(studentDetails.getGroupName());
        return studentRepository.save(student);
    }

    /**
     * Deletes a student by their ID.
     *
     * @param id the ID of the student to delete
     */
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    /**
     * Counts the total number of students.
     *
     * @return the total number of students
     */
    public long countStudents() {
        return studentRepository.count();
    }

    /**
     * Retrieves students by their course ID.
     *
     * @param courseId the ID of the course
     * @return a list of students enrolled in the specified course
     */
    public List<Student> getStudentsByCourse(Long courseId) {
        return studentRepository.findStudentsByCourseId(courseId);
    }

    /**
     * Retrieves students by their course name.
     *
     * @param courseName the name of the course
     * @return a list of students enrolled in the specified course
     */
    public List<Student> getStudentsByCourseName(String courseName) {
        return studentRepository.findStudentsByCourseName(courseName);
    }

    /**
     * Retrieves students by their group and course ID.
     *
     * @param groupId the ID of the group
     * @param courseId the ID of the course
     * @return a list of students in the specified group and course
     */
    public List<Student> getStudentsByGroupIdAndCourseId(Long groupId, Long courseId) {
        return studentRepository.findStudentsByGroupIdAndCourseId(groupId, courseId);
    }

    /**
     * Retrieves students by their group name.
     *
     * @param groupName the name of the group
     * @return a list of students in the specified group
     */
    public List<Student> getStudentsByGroupName(String groupName) {
        return studentRepository.findStudentsByGroupName(groupName);
    }

    /**
     * Retrieves students by their age and course ID.
     *
     * @param age the age of the students
     * @param courseId the ID of the course
     * @return a list of students of the specified age and course
     */
    public List<Student> getStudentsByAgeAndCourse(int age, Long courseId) {
        return studentRepository.findStudentsByAgeAndCourseId(age, courseId);
    }

    public List<Student> getStudentsByGroupNameAndCourseName(String groupName, String courseName) {
        return studentRepository.findStudentsByGroupNameAndCourseName(groupName, courseName);
    }

    public List<Student> getStudentsByAgeGreaterThanOrEqualAndCourse(int age, Long courseId) {
        return studentRepository.findStudentsByAgeGreaterThanOrEqualAndCourseId(age, courseId);
    }
}