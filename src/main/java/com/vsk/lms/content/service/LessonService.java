package com.vsk.lms.content.service;


import com.vsk.lms.content.dto.LessonDto;

import java.util.List;

public interface LessonService {

    LessonDto createLesson(Long courseId, LessonDto lessonDto);

    LessonDto updateLesson(Long lessonId, LessonDto lessonDto);

    void deleteLesson(Long lessonId);

    LessonDto getLessonById(Long lessonId);

    List<LessonDto> getLessonsByCourse(Long courseId);
}
