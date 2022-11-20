package org.example.dao;

import org.example.dao.impl.StudentDAOImpl;
import org.webtech.jaxb.StudentJAXB;
import org.webtech.jaxb.StudentJAXBImpl;

public final class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private final StudentJAXB studentJAXB = new StudentJAXBImpl();
    private final StudentDAO studentDAO = new StudentDAOImpl(studentJAXB);

    private DAOFactory() {}

    public StudentDAO getStudentDAO() {
        return studentDAO;
    }

    public StudentJAXB getStudentJAXB() {
        return studentJAXB;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
