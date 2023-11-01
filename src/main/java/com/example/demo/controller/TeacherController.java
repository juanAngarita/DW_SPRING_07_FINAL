package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DTOs.TeacherDTO;
import com.example.demo.DTOs.TeacherMapper;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailService;
import com.example.demo.security.JWTGenerator;
import com.example.demo.service.TeacherService;

@Controller
@RequestMapping("/teacher")
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    JWTGenerator jwtGenerator;
    
    // http://localhost:8090/teacher/add
    @PostMapping("/add")
    public ResponseEntity addTeacher(@RequestBody Teacher teacher){
        /*
        Teacher teacherDB = teacherService.addTeacher(teacher);
        TeacherDTO newTeacher = TeacherMapper.INSTANCE.convert(teacherDB);
        if(newTeacher == null){
            return new ResponseEntity<TeacherDTO>(newTeacher, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<TeacherDTO>(newTeacher, HttpStatus.CREATED);
        */
        if(userRepository.existsByUsername(teacher.getCorreo())) {
            return new ResponseEntity<String>("Este usuario ya existe",HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = customUserDetailService.saveTeacher(teacher);
        teacher.setUser(userEntity);
        Teacher teacherDB = teacherService.addTeacher(teacher);
        TeacherDTO newTeacher = TeacherMapper.INSTANCE.convert(teacherDB);
        if(newTeacher == null){
            return new ResponseEntity<TeacherDTO>(newTeacher, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<TeacherDTO>(newTeacher, HttpStatus.CREATED);
    }
    

    // http://localhost:8090/student/login
    @PostMapping("/login")
    public ResponseEntity loginEstudiante(@RequestBody() Teacher teacher) {
        /* 
        Teacher teacherDB = teacherService.SearchByCorreo(teacher.getCorreo());

        if (teacherDB == null) {
            return new ResponseEntity<String>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        
        TeacherDTO teacherDTO = TeacherMapper.INSTANCE.convert(teacherDB);
        if(teacher.getPassword().equals(teacher.getPassword())){
            return new ResponseEntity<TeacherDTO>(teacherDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<TeacherDTO>(teacherDTO, HttpStatus.BAD_REQUEST);
        }
*/
                Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(teacher.getCorreo(), teacher.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    // http://localhost:8090/teacher/details
    @GetMapping("/details")
    public ResponseEntity<TeacherDTO> buscarProfesor() {

        Teacher teacher = teacherService.SearchByCorreo(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );

        TeacherDTO teacherDTO = TeacherMapper.INSTANCE.convert(teacher);

        if (teacher == null) {
            return new ResponseEntity<TeacherDTO>(teacherDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TeacherDTO>(teacherDTO, HttpStatus.OK);
    }
}
