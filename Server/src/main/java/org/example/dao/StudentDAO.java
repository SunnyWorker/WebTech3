package org.example.dao;

import org.example.POJO.Student;

public interface StudentDAO {
    Student findStudentById(long id);
    void writeStudent(Student student);
}
