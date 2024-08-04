package com.example.demo.controller;

import com.example.demo.dto.GroupCourseResponseDTO;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "GroupCourse", description = "Group and Course management APIs")
public class GroupCourseController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/course/{courseId}/group/{groupId}/byId")
    @Operation(summary = "Get students and teachers by group and course ID")
    public ResponseEntity<GroupCourseResponseDTO> getStudentsAndTeachersByGroupAndCourse(
            @PathVariable Long groupId,
            @PathVariable Long courseId) {

        List<Student> students = studentService.getStudentsByGroupIdAndCourseId(groupId, courseId);
        List<Teacher> teachers = teacherService.getTeachersByGroupIdAndCourseId(groupId, courseId);

        GroupCourseResponseDTO response = new GroupCourseResponseDTO(students, teachers);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/course/{courseName}/group/{groupName}/byName")
    @Operation(summary = "Get students and teachers by group and course names")
    public ResponseEntity<GroupCourseResponseDTO> getStudentsAndTeachersByGroupAndCourseNames(
            @PathVariable String groupName,
            @PathVariable String courseName) {

        List<Student> students = studentService.getStudentsByGroupNameAndCourseName(groupName, courseName);
        List<Teacher> teachers = teacherService.getTeachersByGroupNameAndCourseName(groupName, courseName);

        GroupCourseResponseDTO response = new GroupCourseResponseDTO(students, teachers);
        return ResponseEntity.ok(response);
    }
}
