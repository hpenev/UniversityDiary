package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

/**
 * Entity class representing a student.
 */
@Entity
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    /**
     * The unique identifier of the student.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the student.
     */
    @NotNull
    @EqualsAndHashCode.Include
    private String name;

    /**
     * The age of the student.
     */
    @NotNull
    @EqualsAndHashCode.Include
    private int age;

    /**
     * The group the student belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "group_name_id")
    private GroupName groupName;

    /**
     * The set of courses the student is enrolled in.
     */
    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;
}