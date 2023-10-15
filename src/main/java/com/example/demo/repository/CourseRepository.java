package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    
    @Query("SELECT COUNT(s) FROM Course c JOIN c.student s WHERE c.name = :courseName")
    Long countStudentsInCourse(String courseName);

    Course findByName(String name);

}
