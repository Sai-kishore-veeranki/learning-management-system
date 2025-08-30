package com.vsk.lms.content.service.serviceImpl;


import com.vsk.lms.content.dto.LessonDto;
import com.vsk.lms.content.entity.Lesson;
import com.vsk.lms.content.repository.LessonRepository;
import com.vsk.lms.content.service.LessonService;
import com.vsk.lms.course.entity.Course;
import com.vsk.lms.course.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonServiceImpl(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public LessonDto createLesson(Long courseId, LessonDto lessonDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDto.getTitle());
        lesson.setContentUrl(lessonDto.getContentUrl());
        lesson.setDuration(lessonDto.getDuration());
        lesson.setLessonOrder(lessonDto.getLessonOrder());
        lesson.setCourse(course); // ✅ Set actual Course entity

        Lesson saved = lessonRepository.save(lesson);
        return mapToDto(saved); // ✅ Convert back to DTO
    }



    @Override
    public LessonDto updateLesson(Long lessonId, LessonDto lessonDto) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        lesson.setTitle(lessonDto.getTitle());
        lesson.setContentUrl(lessonDto.getContentUrl());

        Lesson updated = lessonRepository.save(lesson);
        return mapToDto(updated);
    }

    @Override
    public void deleteLesson(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new RuntimeException("Lesson not found");
        }
        lessonRepository.deleteById(lessonId);
    }

    @Override
    public LessonDto getLessonById(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        return mapToDto(lesson);
    }

    @Override
    public List<LessonDto> getLessonsByCourse(Long courseId) {
        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
        return lessons.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private LessonDto mapToDto(Lesson lesson) {
        LessonDto dto = new LessonDto();
        dto.setTitle(lesson.getTitle());
        dto.setContentUrl(lesson.getContentUrl());
        dto.setDuration(lesson.getDuration());
        dto.setLessonOrder(lesson.getLessonOrder());
        dto.setCourseId(lesson.getCourse().getId());
        return dto;
    }

}
