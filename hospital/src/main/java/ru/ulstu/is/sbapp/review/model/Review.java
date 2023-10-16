package ru.ulstu.is.sbapp.review.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.patient.model.Patient;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name  = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Min(1)
    @Max(5)
    private int gradeDoctor;
    private String textField;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public Review() {
    }

    public Review(int gradeDoctor, String textField, Doctor doctor, Patient patient) {
        this.gradeDoctor = gradeDoctor;
        this.textField = textField;
        this.doctor = doctor;
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        if (!doctor.getReviews().contains(this)) {
            doctor.setReviews(this);
        }
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (!patient.getReviews().contains(this)) {
            patient.setReviews(this);
        }
    }

    public int getGradeDoctor() {
        return gradeDoctor;
    }

    public void setGradeDoctor(int gradeDoctor) {
        this.gradeDoctor = gradeDoctor;
    }

    public String getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
