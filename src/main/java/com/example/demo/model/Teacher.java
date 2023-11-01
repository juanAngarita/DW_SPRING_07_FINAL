package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Teacher {

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity user;

    @Id
    @GeneratedValue
    private Long id;

    //@ManyToMany(mappedBy = "profesores")
    @ManyToMany
    @JoinTable(name="course", joinColumns = @JoinColumn(name="teacher_id"), inverseJoinColumns = @JoinColumn(name="student_id"))
    private List<Student> students = new ArrayList<>();

    private String name;
    @Column(unique=true)
    private String correo;

    @Transient
    private String password;
    
    public Teacher(String name, String correo, String password) {
        this.students = new ArrayList<>();
        this.name = name;
        this.correo = correo;
        this.password = password;
    }

}
