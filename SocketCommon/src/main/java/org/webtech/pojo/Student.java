package org.webtech.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student implements Cloneable {
    private long id;
    private int age;
    private String name;
    private String surname;
    private int course;
    private Faculty faculty;
    private double averageMark;

    public Student() {
    }

    public Student(long id, int age, String name, String surname, int course, Faculty faculty, double averageMark) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.course = course;
        this.faculty = faculty;
        this.averageMark = averageMark;
    }

    public Student(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    @Override
    public String toString() {
        return "Студент "+ id +
                ":\nВозраст = " + age +
                "\nИмя = " + name +
                "\nФамилия = " + surname +
                "\nКурс = " + course +
                "\nФакультет = " + faculty +
                "\nСредний балл = " + averageMark +
                '\n';
    }
}
