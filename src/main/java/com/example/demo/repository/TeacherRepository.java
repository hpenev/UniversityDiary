package com.example.demo.repository;

import com.example.demo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Teacher entities.
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    /**
     * Retrieves a list of teachers by their group and course ID.
     *
     * @param groupName the group of the teachers
     * @param courseId the ID of the course
     * @return a list of teachers in the specified group and course
     */
    @Query("SELECT t FROM Teacher t JOIN t.courses c WHERE t.groupName.name = :groupName AND c.id = :courseId")
    List<Teacher> findTeachersByGroupNameAndCourseId(@Param("groupName") String groupName, @Param("courseId") Long courseId);


    @Query("SELECT t FROM Teacher t JOIN t.courses c WHERE t.groupName.id = :groupId AND c.id = :courseId")
    List<Teacher> findTeachersByGroupIdAndCourseId(@Param("groupId") Long groupId, @Param("courseId") Long courseId);


    @Query("SELECT t FROM Teacher t JOIN t.courses c WHERE t.groupName.name = :groupName AND c.name = :courseName")
    List<Teacher> findTeachersByGroupNameAndCourseName(@Param("groupName") String groupName, @Param("courseName") String courseName);
}