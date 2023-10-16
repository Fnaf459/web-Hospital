package ru.ulstu.is.sbapp.doctor.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
@Table(name  = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private int grade;
    @Size(max = 11, min = 11, message = "Номер телефона должен содержать 11 цифр")
    @Pattern(regexp = "[7-9][0-9]{10}", message = "Номер телефона неверный")
    private String phoneNumber;
    @Email(message = "Почта не соответствует формату")
    private String email;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "doctor_maintenance",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "maintenance_id"))
    private List<Maintenance> maintenances = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(String fullName, int grade, String phoneNumber, String email, Maintenance maintenance, List<Record> records) {
        this.fullName = fullName;
        this.grade = grade;
        this.phoneNumber = phoneNumber;
        this.email = email;
        addMaintenance(maintenance);
    }
    public Doctor(String fullName, String phoneNumber, String email, List<Maintenance> maintenances){
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.maintenances = maintenances;
    }
    public Doctor(String fullName, String phoneNumber, String email){
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public void addMaintenance(Maintenance maintenance) {
        if (this.maintenances == null)
            this.maintenances = new ArrayList<>();

        if (!maintenances.contains(maintenance)) {
            maintenances.add(maintenance);
            maintenance.addDoctor(this);
        }
    }
    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(Review review) {
        if (!reviews.contains(review)) {
            reviews.add(review);
            if (review.getDoctor() != this) {
                review.setDoctor(this);
            }
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
