package ru.ulstu.is.sbapp.doctor.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.review.model.Review;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.stream.Stream;

public class DoctorDTO {
    public Long id;
    public String fullName;
    public String phoneNumber;
    public String email;
    public String maintenancesName;
    public double grade;
    public Long maintenanceCount;
    public Long reviewCount;
    public Map<String, String> reviews;
    public Map<String, String> maintenances;
    public DoctorDTO(){}
    public DoctorDTO(Doctor doctor){
        this.id  = doctor.getId();
        this.fullName = doctor.getFullName();
        this.grade = doctor.getReviews().stream().mapToInt(Review::getGradeDoctor).summaryStatistics().getAverage();
        this.phoneNumber = doctor.getPhoneNumber();
        this.email = doctor.getEmail();
        this.maintenanceCount = (long) doctor.getMaintenances().size();
        maintenances = new HashMap<>();
        if (doctor.getMaintenances() != null) {
            for (var maintenance : doctor.getMaintenances()) {
                maintenances.put(maintenance.getNameMaintenance(), Integer.toString(maintenance.getCost()));
            }
        }
        if (doctor.getMaintenances() != null)
            this.maintenancesName = doctor.getMaintenances().toString();
        this.reviewCount = (long) doctor.getReviews().size();
        reviews = new HashMap<>();
        if (doctor.getReviews() != null) {
            for (var review : doctor.getReviews()) {
                reviews.put(review.getTextField(), Integer.toString(review.getGradeDoctor()));
            }
        }
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMaintenancesName() {
        return maintenancesName;
    }

    public void setMaintenancesName(String maintenancesName) {
        this.maintenancesName = maintenancesName;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
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
