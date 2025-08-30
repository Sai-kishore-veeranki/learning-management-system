package com.vsk.lms.content.dto;



import com.vsk.lms.course.entity.Course;
import lombok.Data;

@Data
public class LessonDto {
    private String title;
    private String contentUrl;
    private int duration;
    private int lessonOrder;
    private Long courseId;
}

