package com.example.demo.dto;

import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import lombok.Data;

import java.util.List;

@Data
public class GroupCourseResponseDTO {

    private List<Student> students;
    private List<Teacher> teachers;

    public GroupCourseResponseDTO(List<Student> students, List<Teacher> teachers) {
        this.students = students;
        this.teachers = teachers;
    }
}