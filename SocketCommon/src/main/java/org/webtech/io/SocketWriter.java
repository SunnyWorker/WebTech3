package org.webtech.io;

import org.webtech.pojo.Student;
import org.webtech.jaxb.StudentJAXB;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

public class SocketWriter {
    private OutputStreamWriter out;
    private StudentJAXB studentJAXB;

    public SocketWriter(OutputStream out, StudentJAXB studentJAXB) {
        this.studentJAXB = studentJAXB;
        this.out = new OutputStreamWriter(out);
    }

    public void sendMessage(String message) {
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendStudentInfo(Student student) {
        StringWriter stringWriter = new StringWriter();
        studentJAXB.marshallStudent(student,stringWriter);
        stringWriter.flush();
        sendMessage(stringWriter.toString());
    }
}
