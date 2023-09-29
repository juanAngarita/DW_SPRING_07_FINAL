package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.HomeWork;
import com.example.demo.service.HomeWorkService;

@RestController
@RequestMapping("/homework")
@CrossOrigin(origins = "http://localhost:4200")
public class HomeWorkController {
    
    @Autowired
    HomeWorkService homeWorkService;

    //http://localhost:8090/homework/all
    @GetMapping("/all")
    public List<HomeWork> mostrarTareas() {
        return homeWorkService.SearchAll();
    }

    //http://localhost:8090/homework/student/1
    @GetMapping("/student/{id}")
    public List<HomeWork> mostrarTareas(@PathVariable("id") Long id) {
        return homeWorkService.SearchByStudentId(id);
    }
}
