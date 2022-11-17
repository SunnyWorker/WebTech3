package org.example.dao.impl;

import org.example.POJO.Student;
import org.example.dao.StudentDAO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Scanner;

public class StudentDAOImpl implements StudentDAO {
    private static JAXBContext jaxbContext;
    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;

    private static long currentId;

    public StudentDAOImpl() {
        try {
            jaxbContext = JAXBContext.newInstance(Student.class);
            marshaller = jaxbContext.createMarshaller();
            unmarshaller = jaxbContext.createUnmarshaller();
            try(Scanner scanner = new Scanner("src/main/resources/sequence_id.txt")){
                currentId = Long.parseLong(scanner.next());
            }
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student findStudentById(long id) {
        try {
            return (Student) unmarshaller.unmarshal(new File("src/main/resources/Student"+id+".xml"));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeStudent(Student student) {
        try {
            marshaller.marshal(student,new File("src/main/resources/Student"+currentId+".xml"));
            currentId++;
            try(FileWriter fileWriter = new FileWriter("src/main/resources/sequence_id.txt")) {
                fileWriter.write(String.valueOf(currentId));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
