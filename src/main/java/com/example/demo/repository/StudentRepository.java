package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Student entities.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    /**
     * Retrieves a list of students by their course ID.
     *
     * @param courseId the ID of the course
     * @return a list of students enrolled in the specified course
     */
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);

    /**
     * Retrieves a list of students by their course name.
     *
     * @param courseName the name of the course
     * @return a list of students enrolled in the specified course
     */
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.name = :courseName")
    List<Student> findStudentsByCourseName(@Param("courseName") String courseName);

    /**
     * Retrieves a list of students by their group ID and course ID.
     *
     * @param groupId the group of the students
     * @param courseId the ID of the course
     * @return a list of students in the specified group and course
     */
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE s.groupName.id = :groupId AND c.id = :courseId")
    List<Student> findStudentsByGroupIdAndCourseId(@Param("groupId") Long groupId, @Param("courseId") Long courseId);


    /**
     * Retrieves a list of students by their group name.
     *
     * @param groupName the name of the group
     * @return a list of students in the specified group
     */
    @Query("SELECT s FROM Student s WHERE s.groupName.name = :groupName")
    List<Student> findStudentsByGroupName(@Param("groupName") String groupName);

    /**
     * Retrieves a list of students by their age and course ID.
     *
     * @param age the age of the students
     * @param courseId the ID of the course
     * @return a list of students of the specified age and course
     */
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE s.age > :age AND c.id = :courseId")
    List<Student> findStudentsByAgeAndCourseId(@Param("age") int age, @Param("courseId") Long courseId);


    @Query("SELECT s FROM Student s WHERE s.groupName.id = :groupId")
    List<Student> findStudentsByGroupNameId(@Param("groupId") Long groupId);

    @Query("SELECT s FROM Student s JOIN s.courses c WHERE s.groupName.name = :groupName AND c.name = :courseName")
    List<Student> findStudentsByGroupNameAndCourseName(@Param("groupName") String groupName, @Param("courseName") String courseName);

    @Query("SELECT s FROM Student s JOIN s.courses c WHERE s.age >= :age AND c.id = :courseId")
    List<Student> findStudentsByAgeGreaterThanOrEqualAndCourseId(@Param("age") int age, @Param("courseId") Long courseId);
}