package org.example;

import org.webtech.Student;
import org.webtech.jaxb.StudentJAXB;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

public class ClientWriter {
    private OutputStreamWriter out;
    private StudentJAXB studentJAXB;
    public ClientWriter(OutputStream out, StudentJAXB studentJAXB) {
        this.studentJAXB = studentJAXB;
        this.out = new OutputStreamWriter(out);
    }

    void sayHello(int role) {
        String hello = "";
        switch (role) {
            case 0:
                hello = "Добро пожаловать, гость!";
                break;
            case 1:
                hello = "Добрый день, пользователь.";
                break;
            case 2:
                hello = "Glad to see you, administrator...";
                break;
        }
        sendMessage(hello);
    }

    void sendMessage(String message) {
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void sendStudentInfo(Student student) {
        StringWriter stringWriter = new StringWriter();
        studentJAXB.marshallStudent(student,stringWriter);
        stringWriter.flush();
        sendMessage(stringWriter.toString());
    }
}
