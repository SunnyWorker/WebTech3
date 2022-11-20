package org.example;

import org.webtech.Student;
import org.webtech.jaxb.StudentJAXB;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Scanner;

public class ServerReader {
    private InputStreamReader in;
    private StudentJAXB studentJAXB;

    private Scanner inScanner;

    public ServerReader(InputStream in, StudentJAXB studentJAXB) {
        this.in = new InputStreamReader(in);
        this.studentJAXB = studentJAXB;
        this.inScanner = new Scanner(in);
    }

    String waitForInput() {
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

    Student receiveStudentInfo() {
        String studentString = waitForInput();
        StringReader stringReader = new StringReader(studentString);
        return studentJAXB.unmarshallStudent(stringReader);
    }
}
