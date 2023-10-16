package ru.ulstu.is.sbapp.record.model;

import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.maintenance.model.Maintenance;
import ru.ulstu.is.sbapp.patient.model.Patient;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name  = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id")
    private Maintenance maintenance;

    public Record() {
    }
    public Record(String date, Patient patient, Maintenance maintenance) {
        this.date = date;
        this.patient = patient;
        this.maintenance = maintenance;
    }
    public Record(String date) {
        this.date = date;
    }
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (!patient.getRecords().contains(this)) {
            patient.setRecords(this);
        }
    }

    public Maintenance getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
        if (!maintenance.getRecords().contains(this)) {
            maintenance.setRecords(this);
        }
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
