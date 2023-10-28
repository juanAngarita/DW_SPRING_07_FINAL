package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.model.UserEntity;
import com.example.demo.service.TeacherService;

@Controller
@RequestMapping("/teacher")
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    // http://localhost:8090/teacher/add
    @PostMapping("/add")
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher){
        Teacher newTeacher = teacherService.addTeacher(teacher);
        if(newTeacher == null){
            return new ResponseEntity<Teacher>(newTeacher, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Teacher>(newTeacher, HttpStatus.CREATED);
    }
    

    // http://localhost:8090/student/login?correo=prueba@postman.com
    @PostMapping("/login")
    public ResponseEntity<Teacher> loginEstudiante(@RequestBody() UserEntity teacherUser) {

        Teacher teacher = teacherService.SearchByCorreo(teacherUser.getEmail());
        if(teacher.getPassword().equals(teacherUser.getPassword())){
            return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
        }else{
            return new ResponseEntity<Teacher>(teacher, HttpStatus.BAD_REQUEST);
        }
    }
}
