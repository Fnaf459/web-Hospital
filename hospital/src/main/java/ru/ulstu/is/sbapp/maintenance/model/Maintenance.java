package ru.ulstu.is.sbapp.maintenance.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.record.model.Record;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name  = "maintenances")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameMaintenance;
    private int cost;
    @ManyToMany(mappedBy = "maintenances")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Doctor> doctors = new ArrayList<>();
    @OneToMany(mappedBy = "maintenance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    public Maintenance() {
    }

    public Maintenance(String nameMaintenance, int cost, Doctor doctor) {
        this.nameMaintenance = nameMaintenance;
        this.cost = cost;
        addDoctor(doctor);
    }

    public Maintenance(String nameMaintenance, int cost, List<Doctor> doctors){
        this.nameMaintenance = nameMaintenance;
        this.cost = cost;
        this.doctors = doctors;
    }

    public void addDoctor(Doctor doctor) {
        if (this.doctors == null)
            this.doctors = new ArrayList<>();

        if (!doctors.contains(doctor)) {
            doctors.add(doctor);
            doctor.addMaintenance(this);
        }
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }
    public void setRecords(Record record) {
        if (!records.contains(record)) {
            records.add(record);
            if (record.getMaintenance() != this) {
                record.setMaintenance(this);
            }
        }
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return nameMaintenance;
    }
}
