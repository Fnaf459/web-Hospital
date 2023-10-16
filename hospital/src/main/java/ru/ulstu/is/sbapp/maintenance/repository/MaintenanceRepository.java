package ru.ulstu.is.sbapp.maintenance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.maintenance.model.Maintenance;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    Maintenance getFirstByNameMaintenance(String name);
}
