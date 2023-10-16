package ru.ulstu.is.sbapp.maintenance.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.maintenance.model.Maintenance;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaintenanceDTO {
    public Long id;
    public String nameMaintenance;
    public int cost;
    public Long doctorCount;
    public String doctorFullName = "";
    public MaintenanceDTO(){}
    public MaintenanceDTO(Maintenance maintenance){
        this.id = maintenance.getId();
        this.nameMaintenance = maintenance.getNameMaintenance();
        this.cost = maintenance.getCost();
        this.doctorCount = (long) maintenance.getDoctors().size();
        if (maintenance.getDoctors() != null)
            this.doctorFullName = maintenance.getDoctors().toString().replace("[", "").replace("]", "");
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }
    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }

    public String getNameMaintenance() {
        return nameMaintenance;
    }
    public void setNameMaintenance(String nameMaintenance) {
        this.nameMaintenance = nameMaintenance;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
}
