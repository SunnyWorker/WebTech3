package org.webtech.io;

import org.webtech.pojo.Student;
import org.webtech.jaxb.StudentJAXB;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

public class SocketReader {
    private InputStreamReader in;
    private StudentJAXB studentJAXB;

    public SocketReader(InputStream in, StudentJAXB studentJAXB) {
        this.in = new InputStreamReader(in);
        this.studentJAXB = studentJAXB;
    }

    public String waitForInput() {
        char[] buffer = new char[255];
        StringBuilder answer = new StringBuilder();
        try {
            in.read(buffer);
            do {
                String readResult = new String(buffer);
                if(readResult.indexOf('\u0000')!=-1) {
                    answer.append(readResult.substring(0,readResult.indexOf('\u0000')));
                }
                else answer.append(readResult);
            }
            while(buffer[254]!='\u0000' && in.read(buffer)!=-1);
        }
        catch (IOException ex) {}
        return answer.toString();
    }

    public Student receiveStudentInfo() {
        String studentString = waitForInput();
        StringReader stringReader = new StringReader(studentString);
        return studentJAXB.unmarshallStudent(stringReader);
    }
}
