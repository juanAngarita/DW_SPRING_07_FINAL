package com.example.demo.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.model.Course;

//Ejecutar una instancia de la aplicacion
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class CourseServiceTestNaive {
    

    @Autowired
    private CourseService courseService;


    @BeforeEach
    public void init(){
        Course course = new Course("Algebra");
        Course course2 = new Course("Algebra2");

        courseService.createCourse(course);
        courseService.createCourse(course2);

        //service -> repository -> base de datos
        
    }

    @Test
    public void CourseService_createCourse_Course(){
    
        //arrange
        Course course = new Course("Algebra");
        Course course2 = new Course("Algebra2");

        //act
        Course newCourse = courseService.createCourse(course);
        newCourse = courseService.createCourse(course2);

        //assert
        Assertions.assertThat(newCourse).isNotNull();
    
    }

    @Test
    public void  CourseService_findAll_CourseList(){
        //arrange

        //act
        List<Course> courses = courseService.findAll();

        //assert
        Assertions.assertThat(courses).isNotNull();
        Assertions.assertThat(courses.size()).isEqualTo(2);
    }
}
