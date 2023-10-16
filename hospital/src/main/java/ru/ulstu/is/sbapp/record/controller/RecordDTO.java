package ru.ulstu.is.sbapp.record.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.maintenance.model.Maintenance;
import ru.ulstu.is.sbapp.record.model.Record;

import java.util.Date;

public class RecordDTO {
    public Long id;
    public String date;
    public int cost;
    public Long patientId;
    public Long maintenanceId;
    public String patientName;
    public String maintenanceName;

    public RecordDTO(){}
    public RecordDTO(Record record){
        this.id = record.getId();
        this.date = record.getDate();
        this.cost = record.getMaintenance().getCost();
        this.patientId = record.getPatient().getId();
        this.maintenanceId = record.getMaintenance().getId();
        this.patientName = record.getPatient().getFullName();
        this.maintenanceName = record.getMaintenance().getNameMaintenance();
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(Long maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
