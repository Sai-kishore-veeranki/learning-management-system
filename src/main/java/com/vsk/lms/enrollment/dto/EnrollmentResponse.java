package com.vsk.lms.enrollment.dto;


import lombok.*;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponse {
    private Long enrollmentId;
    private Long courseId;
    private String courseTitle;
    private double progressPercentage;
    private String lastAccessedLesson;
    private boolean completed;
}