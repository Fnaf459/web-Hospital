package ru.ulstu.is.sbapp.maintenance.service;

public class MaintenanceNotFoundException extends RuntimeException{
    public MaintenanceNotFoundException(Long id) {
        super(String.format("Doctor with id [%s] is not found", id));
    }
}
