package com.example.demo.controller;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

//Pruebas unitarias
//Pruebas integracion 

@WebMvcTest(controllers = StudentController.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class StudentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void StudentController_agregarEstudiante_Student()  throws Exception  {
        Student student = new Student(
            "Juan",
            "Programacion",
            4,
            "juan@juan"
        );

        when(studentService.add(Mockito.any(Student.class))).thenReturn(student);

        ResultActions response = mockMvc.perform(
            post("/student/add")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(student)));

        response.andExpect(status().isCreated())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.nombre").value("Juan"))
        .andExpect(jsonPath("$.carrera").value(student.getCarrera()));
    }

    @Test
    public void StudentController_mostrarEstudiantes_StudentList() throws Exception {

        when(studentService.SearchAll()).thenReturn(
            List.of(
                new Student(
                    "Juan",
                    "Programacion",
                    4,
                    "juan@juan"
                ),
                new Student(
                    "Juan",
                    "Programacion",
                    4,
                    "juan@juan"
                )
            )
        );

        ResultActions reponse = mockMvc.perform(get("/student/all"));


        reponse.andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.size()").value(2));

    }

    @Test
    public void StudentController_mostrarEstudiante2_Student() throws Exception {
        
        when(studentService.SearchById(anyLong())).thenReturn(
            null
        );

        ResultActions response = mockMvc.perform(
            get("/student/find/").param("id", "1")
        );

        response.andExpect(status().isNotFound());

    }

}
