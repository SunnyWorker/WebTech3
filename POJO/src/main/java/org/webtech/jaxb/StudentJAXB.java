package org.webtech.jaxb;

import org.webtech.Student;

import java.io.Reader;
import java.io.Writer;

public interface StudentJAXB {
    void marshallStudent(Student student, Writer writer);
    Student unmarshallStudent(Reader reader);
}
