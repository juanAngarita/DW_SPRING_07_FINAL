package com.example.demo.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
//@Table(name = "STUDENT")
public class Student {
    
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private UserEntity user;

    @Column(name = "NAME")
    private String nombre;
    private String carrera;
    private int semestre;
    @Column(unique=true)
    private String correo;

    //nuevo
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HomeWork> tareas = new ArrayList<>();

    @JsonIgnore
    //@ManyToMany
    @ManyToMany
    @JoinTable(name="course", joinColumns = @JoinColumn(name="student_id"), inverseJoinColumns = @JoinColumn(name="teacher_id"))
    private List<Teacher> profesores = new ArrayList<>();
    
    public Student(String nombre, String carrera, int semestre, String correo) {
        this.nombre = nombre;
        this.carrera = carrera;
        this.semestre = semestre;
        this.correo = correo;
    }

}
