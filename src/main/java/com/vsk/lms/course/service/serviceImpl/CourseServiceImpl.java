package com.vsk.lms.course.service.serviceImpl;

import com.vsk.lms.course.dto.CourseRequest;
import com.vsk.lms.course.dto.CourseResponse;
import com.vsk.lms.course.entity.Course;
import com.vsk.lms.course.repository.CourseRepository;
import com.vsk.lms.course.service.CourseService;
import com.vsk.lms.user.entity.User;
import com.vsk.lms.user.reposirtory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public CourseResponse createCourse(CourseRequest request, String instructorUsername) {
        User instructor = userRepository.findByUsername(instructorUsername)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .instructor(instructor)
                .build();

        return mapToResponse(courseRepository.save(course));
    }

    @Override
    public CourseResponse updateCourse(Long courseId, CourseRequest request, String instructorUsername) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getInstructor().getUsername().equals(instructorUsername)) {
            throw new RuntimeException("You are not authorized to update this course");
        }

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategory(request.getCategory());
        course.setPrice(request.getPrice());

        return mapToResponse(courseRepository.save(course));
    }

    @Override
    public void deleteCourse(Long courseId, String instructorUsername) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getInstructor().getUsername().equals(instructorUsername)) {
            throw new RuntimeException("You are not authorized to delete this course");
        }

        courseRepository.delete(course);
    }

    @Override
    public Page<CourseResponse> getAllCourses(int page, int size) {
        return courseRepository.findAll(PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    @Override
    public Page<CourseResponse> filterByCategory(String category, int page, int size) {
        return courseRepository.findByCategory(category, PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    @Override
    public Page<CourseResponse> filterByPrice(Double price, int page, int size) {
        return courseRepository.findByPriceLessThanEqual(price, PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    private CourseResponse mapToResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .category(course.getCategory())
                .price(course.getPrice())
                .instructorName(course.getInstructor().getUsername())
                .build();
    }
}

