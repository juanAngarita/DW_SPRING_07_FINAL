package com.example.demo.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;

//Ejecutar una instancia de la aplicacion

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CourseServiceTestMock {
    
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    CourseRepository courseRepository;


    @BeforeEach
    public void init(){
        
    }

    @Test
    public void CourseService_createCourse_Course(){
    
        //arrange
        Course course = new Course("Algebra");

        when(courseRepository.save(course)).thenReturn(
            course
        );

        //act
        Course newCourse = courseService.createCourse(course);

        //assert
        Assertions.assertThat(newCourse).isNotNull();
    
    }

    @Test
    public void  CourseService_findAll_CourseList(){
        //arrange
        when(courseRepository.findAll()).thenReturn(
            List.of(
                new Course("Algebra"),
                new Course("Algebra2")
            )
        );

        //act
        List<Course> courses = courseService.findAll();

        //assert
        Assertions.assertThat(courses).isNotNull();
        Assertions.assertThat(courses.size()).isEqualTo(2);
    }

    //Si un curso tiene mas de 5 estudiantes, no se puede agregar un estudiante
    @Test
    public void CourseService_addStudentToCourse_NullCourse() {
    
        when(courseRepository.countStudentsInCourse("SISTEMAS")).thenReturn(5l);

        Course course = courseService.addStudentToCourse(1l, "SISTEMAS");

        Assertions.assertThat(course).isNull();
    }

    @Test
    public void CourseService_addStudentToCourse_Course(){

        when(courseRepository.countStudentsInCourse("SISTEMAS")).thenReturn(4l);

        when(studentRepository.findById(1l)).thenReturn(Optional.ofNullable(new Student()));

        when(courseRepository.findByName("SISTEMAS")).thenReturn(new Course());

        when(courseRepository.save(Mockito.any(Course.class))).thenReturn(new Course());

        //act
        Course course = courseService.addStudentToCourse(1l, "SISTEMAS");

        //assert
        Assertions.assertThat(course).isNotNull();
    }
}
