package com.vsk.lms.enrollment.service.serviceImpl;


import com.vsk.lms.course.entity.Course;
import com.vsk.lms.course.repository.CourseRepository;
import com.vsk.lms.enrollment.dto.EnrollmentRequest;
import com.vsk.lms.enrollment.dto.EnrollmentResponse;
import com.vsk.lms.enrollment.entity.Enrollment;
import com.vsk.lms.enrollment.repository.EnrollmentRepository;
import com.vsk.lms.enrollment.service.EnrollmentService;
import com.vsk.lms.user.entity.User;
import com.vsk.lms.user.reposirtory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    /** Enroll a student into a course */
    @Override
    public EnrollmentResponse enrollStudent(Long studentId, EnrollmentRequest request) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Prevent duplicate enrollments
        enrollmentRepository.findByStudentAndCourse(student, course)
                .ifPresent(e -> { throw new RuntimeException("Already enrolled in this course"); });

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .progressPercentage(0.0)
                .lastAccessedLesson(null)
                .completed(false)
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);

        return mapToResponse(saved);
    }

    /** Get all enrollments of a student */
    @Override
    public List<EnrollmentResponse> getStudentEnrollments(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return enrollmentRepository.findByStudent(student)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /** Update progress */
    @Override
    public EnrollmentResponse updateProgress(Long enrollmentId, double percentage, String lastLesson) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setProgressPercentage(percentage);
        enrollment.setLastAccessedLesson(lastLesson);
        enrollment.setCompleted(percentage >= 100);

        return mapToResponse(enrollmentRepository.save(enrollment));
    }

    /** Map Entity → DTO */
    private EnrollmentResponse mapToResponse(Enrollment enrollment) {
        return EnrollmentResponse.builder()
                .enrollmentId(enrollment.getId())
                .courseId(enrollment.getCourse().getId())
                .courseTitle(enrollment.getCourse().getTitle())
                .progressPercentage(enrollment.getProgressPercentage())
                .lastAccessedLesson(enrollment.getLastAccessedLesson())
                .completed(enrollment.isCompleted())
                .build();
    }
}
