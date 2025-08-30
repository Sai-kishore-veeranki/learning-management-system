package com.vsk.lms.enrollment.repository;


import com.vsk.lms.course.entity.Course;
import com.vsk.lms.enrollment.entity.Enrollment;
import com.vsk.lms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(User student);
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
}

