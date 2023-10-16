package ru.ulstu.is.sbapp.patient.model;

import ru.ulstu.is.sbapp.maintenance.model.Maintenance;
import ru.ulstu.is.sbapp.record.model.Record;
import ru.ulstu.is.sbapp.review.model.Review;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name  = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    @Size(max = 11, min = 11, message = "Номер телефона должен содержать 11 цифр")
    @Pattern(regexp = "[7-9][0-9]{10}", message = "Номер телефона неверный")
    private String phoneNumber;
    @Email(message = "Почта не соответствует формату")
    private String email;
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public Patient() {
    }
    public Patient(String fullName, String phoneNumber, String email) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(Record record) {
        if (!records.contains(record)) {
            records.add(record);
            if (record.getPatient() != this) {
                record.setPatient(this);
            }
        }
    }
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Review review) {
        if (!reviews.contains(review)) {
            reviews.add(review);
            if (review.getPatient() != this) {
                review.setPatient(this);
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

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
