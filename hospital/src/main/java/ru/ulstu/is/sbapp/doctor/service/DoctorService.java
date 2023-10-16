package ru.ulstu.is.sbapp.doctor.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.doctor.repository.DoctorRepository;
import ru.ulstu.is.sbapp.maintenance.model.Maintenance;
import ru.ulstu.is.sbapp.maintenance.repository.MaintenanceRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @PersistenceContext
    private EntityManager em;
    private final DoctorRepository doctorRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final ValidatorUtil validatorUtil;

    public DoctorService(DoctorRepository doctorRepository, MaintenanceRepository maintenanceRepository, ValidatorUtil validatorUtil) {
        this.doctorRepository = doctorRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Doctor addDoctor(String doctorFullName, String doctorPhoneNumber, String doctorEmail) {
        final Doctor doctor = new Doctor(doctorFullName,doctorPhoneNumber, doctorEmail);
        validatorUtil.validate(doctor);
        return doctorRepository.save(doctor);
    }

    @Transactional(readOnly = true)
    public Doctor findDoctor(Long id) {
        final Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElseThrow(() -> new DoctorNotFoundException(id));
    }

    @Transactional
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional
    public Doctor updateDoctor(Long id, String doctorFullName, String doctorPhoneNumber,
                                 String doctorEmail) {
        if (!StringUtils.hasText(doctorFullName)
                && !StringUtils.hasText(doctorPhoneNumber)
                && !StringUtils.hasText(doctorEmail)) {
            throw new IllegalArgumentException("Doctor name, phone number and email is null or empty");
        }
        final Doctor currentDoctor = findDoctor(id);
        currentDoctor.setFullName(doctorFullName);
        currentDoctor.setPhoneNumber(doctorPhoneNumber);
        currentDoctor.setEmail(doctorEmail);
        validatorUtil.validate(currentDoctor);
        return doctorRepository.save(currentDoctor);
    }

    @Transactional
    public Doctor deleteDoctor(Long id) {
        final Doctor currentDoctor = findDoctor(id);
        doctorRepository.delete(currentDoctor);
        return currentDoctor;
    }
}
