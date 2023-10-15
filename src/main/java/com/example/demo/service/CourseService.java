package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Course;

public interface CourseService {

    public Course createCourse(Course course);

    public Course findById(Long id);

    public List<Course> findAll();

    public Course addStudentToCourse(Long studentId, String nombreCurso);
    
}
