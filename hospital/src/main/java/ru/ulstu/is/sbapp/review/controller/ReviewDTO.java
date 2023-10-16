package ru.ulstu.is.sbapp.review.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.record.model.Record;
import ru.ulstu.is.sbapp.review.model.Review;

import java.util.Date;

public class ReviewDTO {
    public Long id;
    public int gradeDoctor;
    private String textField;
    public Long doctorId;
    public Long patientId;
    public ReviewDTO(){}
    public ReviewDTO(Review review){
        this.id = review.getId();
        this.gradeDoctor = review.getGradeDoctor();
        this.textField = review.getTextField();
        this.doctorId = review.getDoctor().getId();
        this.patientId = review.getPatient().getId();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getMaintenanceId() {
        return id;
    }
    public void setMaintenanceId(Long maintenanceId) {
        this.id = maintenanceId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
