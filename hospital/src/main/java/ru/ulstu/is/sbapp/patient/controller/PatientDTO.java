package ru.ulstu.is.sbapp.patient.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.patient.model.Patient;

import java.util.HashMap;
import java.util.Map;

public class PatientDTO {
    public Long id;
    public String fullName;
    public String phoneNumber;
    public String email;
    public Long recordCount;
    public int fullCost;
    public Map<String, String> records;
    public Map<String, String> reviews;
    public PatientDTO(){}
    public PatientDTO(Patient patient){
        this.id  = patient.getId();
        this.fullName = patient.getFullName();
        this.phoneNumber = patient.getPhoneNumber();
        this.email = patient.getEmail();
        this.recordCount = (long) patient.getRecords().size();
        records = new HashMap<>();
        if (patient.getRecords() != null) {
            for (var record : patient.getRecords()) {
                records.put(record.getMaintenance().getNameMaintenance(), record.getMaintenance().getDoctors().toString().replace("[", "").replace("]", ""));
            }
        }
        reviews = new HashMap<>();
        if (patient.getReviews() != null) {
            for (var review : patient.getReviews()) {
                reviews.put(review.getTextField(), Integer.toString(review.getGradeDoctor()));
            }
        }
        if (patient.getRecords() != null) {
            for (var record : patient.getRecords()) {
                this.fullCost += record.getMaintenance().getCost();
            }
        }
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
