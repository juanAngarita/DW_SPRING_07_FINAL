package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Course {
    
    //Id autogenerado
    @Id
    @GeneratedValue
    Long id;

    //Un curso puede tener muchos profesores
    @ManyToOne
    private Teacher teacher;

    //Un curso puede tener muchos estudiantes
    @ManyToOne
    private Student student;

    //justifica la creación de la relación. 
    String name;

    //Constructor con atributos propios de la clase
    //Sin atributos de las relaciones
    public Course(String name) {
        this.name = name;
    }

    //Constructor vacio
    public Course() {
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
