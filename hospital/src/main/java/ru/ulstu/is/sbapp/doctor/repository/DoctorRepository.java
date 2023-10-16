package ru.ulstu.is.sbapp.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.maintenance.model.Maintenance;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor getFirstByFullName(String name);
}
