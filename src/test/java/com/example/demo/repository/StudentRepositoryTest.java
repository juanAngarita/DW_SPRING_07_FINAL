package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;

@DataJpaTest
@RunWith(SpringRunner.class)
public class StudentRepositoryTest {
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void init(){
        studentRepository.save( new Student( "Juan1", "sistemas", 1, "juan1@juan"));
        studentRepository.save( new Student( "Juan2", "matematicas", 2, "juan2@juan"));
        studentRepository.save( new Student( "Juan3", "sistemas", 3, "juan3@juan"));
        studentRepository.save( new Student( "Juan4", "sistemas", 4, "juan4@juan"));
    
        teacherRepository.save( new Teacher( "Pablo", "Pablo@prueba.edu.co", "123456"));
        teacherRepository.save( new Teacher( "Nicolas", "Nicolas@prueba.edu.co", "123456"));

        //asociar estudiante y profesor
        Student student = studentRepository.findById(1l).get();
        Teacher teacher = teacherRepository.findById(1l).get();

        //asociar estudiante, curso y profesor
        Course course = courseRepository.save(new Course("Programacion"));
        course.setStudent(student);
        course.setTeacher(teacher);

        course = courseRepository.save(new Course("Programacion Avanzada"));
        course.setStudent(studentRepository.findById(2l).get());
        course.setTeacher(teacher);
    }

    @Test
    public void StudentRepository_save_Student() {
        //1.arrange
        //2. act
        //3. assert

        //arrange
        Student student = new Student( "Juan", "Programacion", 4, "juan@juan");

        //act
        Student savedStudent = studentRepository.save(student);

        //assert
        Assertions.assertThat(savedStudent).isNotNull();
    }

    @Test
    public void StudentRepository_FindAll_NotEmpyList() {
        
        //arrange
        Student student = new Student( "Juan", "Programacion", 4, "juan@juan");
        Student student2 = new Student( "Juan", "Programacion", 4, "juan@juan");

        //act
        studentRepository.save(student);
        studentRepository.save(student2);
        List<Student> students = studentRepository.findAll();

        //assert
        Assertions.assertThat(students).isNotNull();
        Assertions.assertThat(students.size()).isEqualTo(6);
        Assertions.assertThat(students.size()).isGreaterThan(0);

    }

    @Test
    public void StuydentRepository_findById_FindWrongIndex(){
        //arrange
        Long index = -1l;

        //act
        Optional<Student> student = studentRepository.findById(index);

        //assert
        Assertions.assertThat(student).isEmpty();
    }

    @Test
    public void StudentRepository_deleteById_EmptyStudent(){
    
    //arrange
    Long index = 2L;

    //act
    studentRepository.deleteById(index);

    //assert
    Assertions.assertThat(studentRepository.findById(index)).isEmpty();
    }

    @Test
    void StudentRepository_findByNombre_Student(){
    
        //arrange
        String nombre = "Juan1";

        //act
        Student student = studentRepository.findByNombre(nombre);
    
        //assert
        Assertions.assertThat(student).isNotNull();
    }

    @Test
    void StudentRepository_updateByName_Student(){
    
        //arrange
        String nombre = "Juan1";

        //act
        Student student = studentRepository.findByNombre(nombre);
        student.setNombre("Modificado");
        Student updated = studentRepository.save(student);
    
        //assert
        Assertions.assertThat(updated).isNotNull();
        Assertions.assertThat(updated.getNombre()).isEqualTo("Modificado");
    }

    @Test
    void StudentRepository_findStudentsFromTeacher_StudentList(){
    
        //arrange
        String id = "1";

        //act
        List<Student> students = studentRepository.findStudentsFromTeacher(id);

        //assert
        Assertions.assertThat(students).isNotNull();
        Assertions.assertThat(students.size()).isGreaterThan(0);
        Assertions.assertThat(students.size()).isEqualTo(2);
    
    }

}
