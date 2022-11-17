package org.example.dao;

import org.example.dao.impl.StudentDAOImpl;

public final class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final StudentDAO studentDAO = new StudentDAOImpl();

    private DAOFactory() {}

    public StudentDAO getStudentDAO() {
        return studentDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
