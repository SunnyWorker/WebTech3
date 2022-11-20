package org.webtech.jaxb;

import org.webtech.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.Writer;

public class StudentJAXBImpl implements StudentJAXB{
    private JAXBContext jaxbContext;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public StudentJAXBImpl() {
        try {
            jaxbContext = JAXBContext.newInstance(Student.class);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void marshallStudent(Student student, Writer writer) {
        try {
            if(student!=null)
                marshaller.marshal(student,writer);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student unmarshallStudent(Reader reader) {
        try {
            return (Student) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }
}
