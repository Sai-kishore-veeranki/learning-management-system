package com.vsk.lms.enrollment.service;



import com.vsk.lms.enrollment.dto.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {

    EnrollmentResponse enrollStudent(Long courseId, Long studentId);

    List<EnrollmentResponse> getStudentEnrollments(Long studentId);

    EnrollmentResponse updateProgress(Long enrollmentId, double percentage, String lastLesson);
}

