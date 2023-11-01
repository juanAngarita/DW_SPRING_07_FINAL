package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailService;
import com.example.demo.security.JWTGenerator;
import com.example.demo.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTGenerator jwtGenerator;

    //http://localhost:8090/student/all
    @GetMapping("/all")
    @Operation(summary = "Encuentra todos los estudiantes")
    public ResponseEntity<List<Student>> mostrarEstudiantes() {

        List<Student> lista = studentService.SearchAll();

        ResponseEntity<List<Student>> response = new ResponseEntity<>(lista, HttpStatus.OK);
        return response;
    }

    // http://localhost:8090/student/find?id=1
    @GetMapping("/find")
    public ResponseEntity<Student> mostrarInfoEstudiante(@RequestParam("id") Long id) {

        Student student = studentService.SearchById(id);

        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    // http://localhost:8090/student/login?correo=prueba@postman.com
    @PostMapping("/login")
    public ResponseEntity loginEstudiante(@RequestBody Student student) {
/* 
        student = studentService.SearchByCorreo(student.getCorreo());
        if (student == null) {
            return new ResponseEntity<Student>(student, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Student>(student, HttpStatus.OK);
        */
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(student.getCorreo(), "123"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    // http://localhost:8090/student/datails
    @GetMapping("/details")
    public ResponseEntity<Student> buscarEstudiante() {

        Student student = studentService.SearchByCorreo(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );

        if (student == null) {
            return new ResponseEntity<Student>(student, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    // http://localhost:8090/student/find/1
    @GetMapping("/find/{id}")
    public  ResponseEntity<Student> mostrarInfoEstudiante2(@PathVariable("id") Long id) {
        Student student = studentService.SearchById(id);
        if (student == null) {
            return new ResponseEntity<Student>(student, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    // http://localhost:8090/student/add
    @PostMapping("/add")
    public ResponseEntity<Student> agregarEstudiante(@RequestBody Student student){
        /*
        Student newStudent = studentService.add(student);
        if(newStudent == null){
            return new ResponseEntity<Student>(newStudent, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Student>(newStudent, HttpStatus.CREATED);
        */

        if(userRepository.existsByUsername(student.getCorreo())) {
            return new ResponseEntity<Student>(student, HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = customUserDetailService.saveStudent(student);
        student.setUser(userEntity);
        Student newStudent = studentService.add(student);
        if(newStudent == null){
            return new ResponseEntity<Student>(newStudent, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Student>(newStudent, HttpStatus.CREATED);
    }

    //delete
    //http://localhost:8090/student/delete/1
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarEstudiante(@PathVariable("id") Long id){
        studentService.deleteById(id);
        return new ResponseEntity<>("DELETED",HttpStatus.NO_CONTENT);
    }

    //http://localhost:8090/student/update/1
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> actualizarEstudiante(@RequestBody Student student, @PathVariable("id") Long id){
        Student studentFind = studentService.SearchById(id);
        student.setId(studentFind.getId());
        Student studentUpdated = studentService.update(student);
        return new ResponseEntity<>(studentUpdated,HttpStatus.OK);
    }


    @GetMapping("/teacher/{id}")
    public String mostrarEstudiantesDeProfesor(@PathVariable("id") String id, Model model){
        model.addAttribute("estudiantes", studentService.findStudentsFromTeacher(id));
        return "mostrar_todos_estudiantes";
    }


    @GetMapping("/homework/{type}")
    public String mostrarEstudiantePorTrabajos(@PathVariable("type") String type, Model model){
        model.addAttribute("estudiantes", studentService.findStudentsByHomework(type));
        return "mostrar_todos_estudiantes";
    }


}
