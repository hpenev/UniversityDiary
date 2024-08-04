package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Entity class representing a teacher.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    /**
     * The unique identifier of the teacher.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the teacher.
     */
    @NotNull
    private String name;

    /**
     * The age of the teacher.
     */
    @NotNull
    private int age;

    /**
     * The group the teacher belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "group_name_id")
    private GroupName groupName;

    /**
     * The set of courses the teacher is teaching.
     */
    @ManyToMany
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;
}