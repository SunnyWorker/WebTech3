package org.example.dao.impl;

import org.example.dao.StudentDAO;
import org.webtech.Student;
import org.webtech.jaxb.StudentJAXB;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class StudentDAOImpl implements StudentDAO {
    private static long currentId;
    private static StudentJAXB studentJAXB;
    public StudentDAOImpl(StudentJAXB studentJAXB) {
        this.studentJAXB = studentJAXB;
        try(Scanner scanner = new Scanner(new File("src/main/resources/sequence_id.txt"))){
            currentId = Long.parseLong(scanner.next());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void incrementCurrentId() {
        currentId++;
        try(FileWriter fileWriter = new FileWriter("src/main/resources/sequence_id.txt")) {
            fileWriter.write(String.valueOf(currentId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student findStudentById(long id) {
        try(FileReader fileReader = new FileReader("src/main/resources/Student"+id+".xml")) {
            return studentJAXB.unmarshallStudent(fileReader);
        } catch (IOException e) {
            return new Student(-1);
        }
    }

    @Override
    public void writeStudent(Student student) {
        try(FileWriter fileWriter = new FileWriter("src/main/resources/Student"+student.getId()+".xml")) {
            studentJAXB.marshallStudent(student,fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteStudentById(long id) {
        try {
            Files.deleteIfExists(new File("src/main/resources/Student"+id+".xml").toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long requestAvailableId() {
        incrementCurrentId();
        return currentId-1;
    }
}
