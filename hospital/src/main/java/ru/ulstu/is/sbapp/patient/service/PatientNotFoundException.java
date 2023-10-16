package ru.ulstu.is.sbapp.patient.service;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long id) {
        super(String.format("Patient with id [%s] is not found", id));
    }
}
