package ru.ulstu.is.sbapp.doctor.service;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(Long id) {
        super(String.format("Doctor with id [%s] is not found", id));
    }
}
