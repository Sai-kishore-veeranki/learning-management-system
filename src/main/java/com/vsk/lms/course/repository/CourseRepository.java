package com.vsk.lms.course.repository;


import com.vsk.lms.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByCategory(String category, Pageable pageable);
    Page<Course> findByPriceLessThanEqual(Double price, Pageable pageable);
}

