package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repo;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Course createCourse(Course course) {
        //usa repositorio en la funcion save
        return repo.save(course);
    }

    @Override
    public Course findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Course> findAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    @Override
    public Course addStudentToCourse(Long studentId, String nombreCurso) {

        Long cantidad = repo.countStudentsInCourse(nombreCurso);

        if (cantidad >= 5) {
            return null;
        }else{
            Student student = studentRepository.findById(studentId).get();
            Course course = repo.findByName(nombreCurso);
            course.setStudent(student);
            return repo.save(course);
        }

    }
    
}
