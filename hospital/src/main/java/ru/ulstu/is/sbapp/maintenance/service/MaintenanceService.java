package ru.ulstu.is.sbapp.maintenance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.doctor.repository.DoctorRepository;
import ru.ulstu.is.sbapp.doctor.service.DoctorNotFoundException;
import ru.ulstu.is.sbapp.doctor.service.DoctorService;
import ru.ulstu.is.sbapp.maintenance.model.Maintenance;
import ru.ulstu.is.sbapp.maintenance.repository.MaintenanceRepository;
import ru.ulstu.is.sbapp.record.model.Record;
import ru.ulstu.is.sbapp.record.repository.RecordRepository;
import ru.ulstu.is.sbapp.record.service.RecordNotFoundException;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {
    @PersistenceContext
    private EntityManager em;
    private final MaintenanceRepository maintenanceRepository;
    private final DoctorService doctorService;
    private final RecordRepository recordRepository;
    private final ValidatorUtil validatorUtil;
    private final DoctorRepository doctorRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository, RecordRepository recordRepository,
                              ValidatorUtil validatorUtil, DoctorService doctorService, DoctorRepository doctorRepository) {
        this.recordRepository = recordRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.validatorUtil = validatorUtil;
        this.doctorService = doctorService;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Maintenance addMaintenance(String maintenanceName, int maintenanceCost, Doctor doctor) {
        final Maintenance maintenance = new Maintenance(maintenanceName, maintenanceCost, doctor);
        validatorUtil.validate(maintenance);
        return maintenanceRepository.save(maintenance);
    }

    @Transactional(readOnly = true)
    public Maintenance findMaintenance(Long id) {
        final Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        return maintenance.orElseThrow(() -> new MaintenanceNotFoundException(id));
    }

    @Transactional
    public List<Maintenance> findAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    @Transactional
    public Doctor getByNameDoctor(String str){return doctorRepository.getFirstByFullName(str);}

    @Transactional
    public Record findRecord(Long id) {
        final Optional<Record> record = recordRepository.findById(id);
        return record.orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Transactional
    public Doctor findDoctor(Long id) {
        final Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElseThrow(() -> new DoctorNotFoundException(id));
    }
    @Transactional
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional
    public Maintenance updateMaintenance(Long id, String maintenanceName, int maintenanceCost, Doctor doctor) {
        if (!StringUtils.hasText(maintenanceName) && maintenanceCost == 0) {
            throw new IllegalArgumentException("Maintenance name and cost is null or empty");
        }
        final Maintenance currentMaintenance = findMaintenance(id);
        currentMaintenance.setNameMaintenance(maintenanceName);
        currentMaintenance.setCost(maintenanceCost);
        currentMaintenance.addDoctor(doctor);
        validatorUtil.validate(currentMaintenance);
        return maintenanceRepository.save(currentMaintenance);
    }

    @Transactional
    public Maintenance deleteMaintenance(Long id) {
        final Maintenance currentMaintenance = findMaintenance(id);
        maintenanceRepository.delete(currentMaintenance);
        return currentMaintenance;
    }
}
