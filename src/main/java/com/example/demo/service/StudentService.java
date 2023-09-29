package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import com.example.demo.model.Student;

public interface StudentService {
    
    public Student SearchById(Long id);

    public List<Student> SearchAll();

    public void deleteById(Long id);

    public void update(Student student);

    public void add(Student student);

    //nuevo 
    List<Student> findStudentsFromTeacher(String teacherID);

    List<Student> findStudentsByHomework(String type);

}
