package ru.ulstu.is.sbapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HospitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }
    //Сущности: Деканат, Студенты, Курсы, Преподавтели (Deanery, Student, Course, Teacher)
    //Функции: Зачисление студентов, перевод стужентов между курсами, отчисление студентов
}
