package com.vsk.lms.course.service;

import com.vsk.lms.course.dto.CourseRequest;
import com.vsk.lms.course.dto.CourseResponse;
import org.springframework.data.domain.Page;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request, String instructorUsername);
    CourseResponse updateCourse(Long courseId, CourseRequest request, String instructorUsername);
    void deleteCourse(Long courseId, String instructorUsername);
    Page<CourseResponse> getAllCourses(int page, int size);
    Page<CourseResponse> filterByCategory(String category, int page, int size);
    Page<CourseResponse> filterByPrice(Double price, int page, int size);
    CourseResponse getCourseById(Long courseId);
}

