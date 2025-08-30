package com.vsk.lms.course.controller;


import com.vsk.lms.course.dto.CourseRequest;
import com.vsk.lms.course.dto.CourseResponse;
import com.vsk.lms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest request,
                                                       Authentication authentication) {
        String instructorUsername = authentication.getName();
        return ResponseEntity.ok(courseService.createCourse(request, instructorUsername));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id,
                                                       @RequestBody CourseRequest request,
                                                       Authentication authentication) {
        String instructorUsername = authentication.getName();
        return ResponseEntity.ok(courseService.updateCourse(id, request, instructorUsername));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id,
                                               Authentication authentication) {
        String instructorUsername = authentication.getName();
        courseService.deleteCourse(id, instructorUsername);
        return ResponseEntity.ok("Course deleted successfully");
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CourseResponse>> getAllCourses(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.getAllCourses(page, size));
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CourseResponse>> getByCategory(@PathVariable String category,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.filterByCategory(category, page, size));
    }

    @GetMapping("/price/{price}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CourseResponse>> getByPrice(@PathVariable Double price,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.filterByPrice(price, page, size));
    }
}

