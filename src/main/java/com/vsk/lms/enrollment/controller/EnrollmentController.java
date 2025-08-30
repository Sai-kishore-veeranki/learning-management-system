package com.vsk.lms.enrollment.controller;


import com.vsk.lms.enrollment.dto.EnrollmentRequest;
import com.vsk.lms.enrollment.dto.EnrollmentResponse;
import com.vsk.lms.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;


    @PostMapping("/{studentId}")
    @PreAuthorize("hasRole('STUDENT') and #studentId.toString() == authentication.principal.id.toString()")
    public ResponseEntity<EnrollmentResponse> enroll(
            @PathVariable Long studentId,
            @RequestBody EnrollmentRequest request
    ) {
        return ResponseEntity.ok(enrollmentService.enrollStudent(studentId, request));
    }


    @GetMapping("/{studentId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('STUDENT') and #studentId.toString() == authentication.principal.id.toString())")
    public ResponseEntity<List<EnrollmentResponse>> getEnrollments(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getStudentEnrollments(studentId));
    }


    @PutMapping("/{enrollmentId}/progress")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<EnrollmentResponse> updateProgress(
            @PathVariable Long enrollmentId,
            @RequestParam double percentage,
            @RequestParam String lastLesson
    ) {
        return ResponseEntity.ok(enrollmentService.updateProgress(enrollmentId, percentage, lastLesson));
    }
}

