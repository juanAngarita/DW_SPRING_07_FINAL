package com.example.demo.service;

import com.example.demo.model.Teacher;

public interface TeacherService {

    public Teacher SearchById(Long id);

    public Teacher addTeacher(Teacher teacher);
    
    public Teacher SearchByCorreo(String correo);

    
}
