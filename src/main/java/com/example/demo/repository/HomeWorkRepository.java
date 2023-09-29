package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.HomeWork;

@Repository
public interface HomeWorkRepository extends JpaRepository<HomeWork, Long> {
    //Busca todas las tareas relacionadas con un estudiante
    List<HomeWork> findByEstudianteId(Long id);
}
