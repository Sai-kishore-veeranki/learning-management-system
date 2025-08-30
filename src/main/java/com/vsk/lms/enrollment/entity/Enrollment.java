package com.vsk.lms.enrollment.entity;



import com.vsk.lms.course.entity.Course;
import com.vsk.lms.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-One: Student → Enrollment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // Many-to-One: Course → Enrollment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private double progressPercentage = 0.0; // Track % completed
    private String lastAccessedLesson;       // Track last lesson/section

    private boolean completed = false;
}
