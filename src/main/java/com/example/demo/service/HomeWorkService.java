package com.example.demo.service;

import java.util.List;

import com.example.demo.model.HomeWork;

public interface HomeWorkService {
    
    public HomeWork SearchById(Long id);

    public List<HomeWork> SearchAll();

    public List<HomeWork> SearchByStudentId(Long id);

}
