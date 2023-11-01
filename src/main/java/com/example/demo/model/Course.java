package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
