package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

/**
 * Entity class representing a course.
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    /**
     * The unique identifier of the course.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the course.
     * This field is mandatory.
     */
    @NotBlank(message = "Course name is mandatory")
    @EqualsAndHashCode.Include
    private String name;

    /**
     * The type of the course.
     * This field is mandatory.
     */
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    @Valid
    @NotNull(message = "Course type is mandatory")
    @EqualsAndHashCode.Include
    private CourseType type;

    /**
     * The set of students enrolled in the course.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Student> students;

    /**
     * The set of teachers teaching the course.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Teacher> teachers;
}