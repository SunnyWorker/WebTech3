package org.example;

import org.example.dao.DAOFactory;
import org.example.dao.StudentDAO;
import org.webtech.Student;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ClientReader clientReader;
    private ClientWriter clientWriter;
    private Student student;
    private StudentDAO studentDAO;
    int role; // 0 - no rights, 1 - read only, 2 - full control

    public Client(Socket socket) {
        this.socket = socket;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            clientReader = new ClientReader(socket.getInputStream(),daoFactory.getStudentJAXB());
            clientWriter = new ClientWriter(socket.getOutputStream(),daoFactory.getStudentJAXB());
            studentDAO = DAOFactory.getInstance().getStudentDAO();
            waitForCommand();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class ClientRequests implements Runnable {
        @Override
        public void run() {
            role = Integer.parseInt(clientReader.waitForInput());
            clientWriter.sayHello(role);
            int command = 0;
            while(command!=5) {
                command = Integer.parseInt(clientReader.waitForInput());
                if(checkCommandForAvailability(command)) {
                    clientWriter.sendMessage("1");
                    switch (command) {
                        case 1:
                            requestReport();
                            break;
                        case 2:
                            createNewReport();
                            break;
                        case 3:
                            changeReport();
                            break;
                        case 4:
                            deleteReport();
                            break;
                        case 5:
                            ClientsConnection.closeClient(Client.this);
                            break;
                    }
                }
                else clientWriter.sendMessage("0");
            }
        }
    }

    private boolean checkCommandForAvailability(int command) {
        if(command==5) return true;
        else if(role>0 && command==1) return true;
        else if(role>1 && command==2) return true;
        else if(role>1 && student!=null) return true;
        return false;
    }

    private void requestReport() {
        long id = Long.parseLong(clientReader.waitForInput());
        student = studentDAO.findStudentById(id);
        clientWriter.sendStudentInfo(student);
    }

    private void changeReport() {
        Student student = clientReader.receiveStudentInfo();
        student.setId(this.student.getId());
        if(!this.student.equals(student)) {
            studentDAO.writeStudent(student);
            this.student = student;
        }
    }

    private void createNewReport() {
        clientWriter.sendMessage(String.valueOf(studentDAO.requestAvailableId()));
        Student student = clientReader.receiveStudentInfo();
        this.student = student;
        studentDAO.writeStudent(student);
    }

    private void deleteReport() {
        studentDAO.deleteStudentById(student.getId());
        student = null;
    }

    private void waitForCommand() {
        Thread thread = new Thread(new ClientRequests());
        thread.start();
    }

    void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
