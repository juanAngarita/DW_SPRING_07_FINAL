package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //select * from student where Name = nombre
    Student findByNombre(String name);

    @Query("select s from Student s where s.id < 4")
    List<Student> findFirst3Students();

    @Query("select s from Student s where s.id < ?1")
    List<Student> findFirstNStudents(int cantidad);

    @Query("select s from Course c inner join c.student s where c.teacher.id = ?1")
    List<Student> findStudentsFromTeacher(String teacherID);

    @Query("select s from HomeWork h join h.estudiante s where h.name LIKE %?1% ")
    List<Student> findStudentsByTypeOfHomework(String type);
}

