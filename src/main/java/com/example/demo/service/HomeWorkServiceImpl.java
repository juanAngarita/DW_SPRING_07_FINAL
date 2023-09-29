package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.HomeWork;
import com.example.demo.repository.HomeWorkRepository;

@Service
public class HomeWorkServiceImpl implements HomeWorkService {

    @Autowired
    HomeWorkRepository repo;

    @Override
    public HomeWork SearchById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<HomeWork> SearchAll() {
        return repo.findAll();
    }

    @Override
    public List<HomeWork> SearchByStudentId(Long id) {
        return repo.findByEstudianteId(id);
    }
    
}
