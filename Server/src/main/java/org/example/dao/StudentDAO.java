package org.example.dao;


import org.webtech.pojo.Student;

public interface StudentDAO {
    Student findStudentById(long id);
    void writeStudent(Student student);
    void deleteStudentById(long id);
    long requestAvailableId();
}
