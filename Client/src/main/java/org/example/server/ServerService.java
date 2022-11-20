package org.example.server;

import org.example.Account;
import org.webtech.io.SocketReader;
import org.webtech.io.SocketWriter;
import org.webtech.jaxb.StudentJAXB;
import org.webtech.jaxb.StudentJAXBImpl;
import org.webtech.pojo.Faculty;
import org.webtech.pojo.Student;

import java.io.IOException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ServerService {
    private SocketReader serverReader;
    private SocketWriter serverWriter;
    private Student student;
    private Scanner userConsole;

    public ServerService(Socket socket, Account account) {
        try {
            StudentJAXB studentJAXB = new StudentJAXBImpl();
            this.serverReader = new SocketReader(socket.getInputStream(), studentJAXB);
            this.serverWriter = new SocketWriter(socket.getOutputStream(), studentJAXB);
            this.student = null;
            this.userConsole = new Scanner(System.in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        serverWriter.sendMessage(String.valueOf(account.getRole()));
        System.out.println(serverReader.waitForInput());
        chooseCommand();
    }

    private void chooseCommand() {
        int command = 0;
        byte permission;
        while (command!=5) {
            printMenu();
            try {
                command = Integer.parseInt(userConsole.nextLine());
                if(command!=0) {
                    serverWriter.sendMessage(String.valueOf(command));
                    permission = Byte.parseByte(serverReader.waitForInput());
                }
                else permission=1;

                if (permission == 1) {
                    switch (command) {
                        case 0:
                            if(student!=null) System.out.println(student);
                            else System.err.println("Ни одно дело не открыто!");
                            break;
                        case 1:
                            requestReport();
                            break;
                        case 2:
                            createNewReport();
                            break;
                        case 3:
                            if (student != null) changeReport();
                            else System.err.println("Команда не найдена! Выберите другую!");
                            break;
                        case 4:
                            if (student != null) {
                                System.out.println("Удаление прошло успешно!");
                                student = null;
                            }
                            else {
                                System.err.println("Команда не найдена! Выберите другую!");
                            }
                            break;
                        case 5:
                            System.out.println("Меню закрыто!");
                            ServerConnection.closeConnection();
                            return;
                        default:
                            System.err.println("Команда не найдена! Выберите другую!");
                            break;
                    }
                }
                else if(permission==0) System.err.println("Ваши текущие права не позволяют использовать данные команды!\n");
                else System.err.println("Ни одно дело не открыто!");
                System.err.println("Нажмите ENTER для продолжения...");
                userConsole.nextLine();
            }
            catch (InputMismatchException | NumberFormatException ex) {
                System.err.println("Введённые данные не являются числом! Повторите ввод!");
                System.err.println("Нажмите ENTER для продолжения...");
                userConsole.nextLine();
            }
        }
    }

    private void changeReport() {
        int command = 1;
        Student oldStudent = null;
        try {
            oldStudent = (Student) student.clone();
        }catch (CloneNotSupportedException exception) {

        }
        while(command<7) {
            changingMenu();
            try {
                command = userConsole.nextInt();
                userConsole.nextLine();
                switch (command) {
                    case 1:
                        System.out.println("Установите новый возраст...");
                        student.setAge(userConsole.nextInt());
                        break;
                    case 2:
                        System.out.println("Установите новое имя...");
                        student.setName(userConsole.nextLine());
                        break;
                    case 3:
                        System.out.println("Установите новую фамилию...");
                        student.setSurname(userConsole.nextLine());
                        break;
                    case 4:
                        System.out.println("Установите новый курс...");
                        student.setCourse(userConsole.nextInt());
                        break;
                    case 5:
                        System.out.println("Установите новый факультет...");
                        System.out.println("Факультеты: FCSAN, FRE, FICT, FCP, FIS");
                        student.setFaculty(Faculty.valueOf(userConsole.nextLine()));
                        break;
                    case 6:
                        System.out.println("Установите новую среднюю оценку...");
                        student.setAverageMark(userConsole.nextDouble());
                        break;
                    case 7:
                        serverWriter.sendStudentInfo(student);
                        System.out.println("Изменения отправлены!");
                        break;
                    case 8:
                        serverWriter.sendStudentInfo(oldStudent);
                        student = oldStudent;
                        System.out.println("Изменение отменено!");
                        break;
                    default:
                        System.out.println("Команда не найдена! Выберите другую!");
                }
            }
            catch (InputMismatchException ex) {
                System.err.println("Формат введённых данных не соответствует ожидаемому формату. Повторите попытку!");
            }
        }
    }

    private void createNewReport() {
        long id = Long.parseLong(serverReader.waitForInput());
        student = new Student(id);
        serverWriter.sendStudentInfo(student);
        System.out.println("Cтудент с id = "+id+" добавлен успешно!");
    }

    private void requestReport() {
        System.out.println("Введите ID студента для поиска...");
        long id = Long.parseLong(userConsole.nextLine());
        serverWriter.sendMessage(String.valueOf(id));
        Student student = serverReader.receiveStudentInfo();
        if(student.getId()==-1) {
            System.err.println("Студент с данным ID не найден...");
        }
        else this.student = student;
    }

    private void printMenu() {
        System.out.println("Клиентское меню:");
        System.out.println("0 - просмотреть дело");
        System.out.println("1 - найти дело студента");
        System.out.println("2 - создать новое дело");
        if(student!=null) {
            System.out.println("3 - изменить дело");
            System.out.println("4 - удалить дело");
        }
        System.out.println("5 - разорвать связь с сервером");
        System.out.println("Выбор...");
    }

    private void changingMenu() {
        System.out.println("Клиентское меню:");
        System.out.println("1 - изменить возраст");
        System.out.println("2 - изменить имя");
        System.out.println("3 - изменить фамилию");
        System.out.println("4 - изменить курс");
        System.out.println("5 - изменить факультет");
        System.out.println("6 - изменить среднюю оценку");
        System.out.println("7 - отправить изменения на сервер");
        System.out.println("8 - отмена");
        System.out.println("Выбор...");
    }
}
