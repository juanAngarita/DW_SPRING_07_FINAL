package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Teacher {

    @Id
    @GeneratedValue
    private Long id;

    //@ManyToMany(mappedBy = "profesores")
    @ManyToMany
    @JoinTable(name="course", joinColumns = @JoinColumn(name="teacher_id"), inverseJoinColumns = @JoinColumn(name="student_id"))
    private List<Student> students = new ArrayList<>();

    private String name;
    private String correo;
    private String password;
    
    public Teacher(String name, String correo, String password) {
        this.students = new ArrayList<>();
        this.name = name;
        this.correo = correo;
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Teacher() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
