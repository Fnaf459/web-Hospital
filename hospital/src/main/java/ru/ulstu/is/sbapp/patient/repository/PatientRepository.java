package ru.ulstu.is.sbapp.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.patient.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient getFirstByFullName(String name);
}
