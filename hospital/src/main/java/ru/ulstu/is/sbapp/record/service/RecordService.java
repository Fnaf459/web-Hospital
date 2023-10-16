package ru.ulstu.is.sbapp.record.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.doctor.service.DoctorService;
import ru.ulstu.is.sbapp.maintenance.model.Maintenance;
import ru.ulstu.is.sbapp.maintenance.repository.MaintenanceRepository;
import ru.ulstu.is.sbapp.maintenance.service.MaintenanceService;
import ru.ulstu.is.sbapp.patient.model.Patient;
import ru.ulstu.is.sbapp.patient.repository.PatientRepository;
import ru.ulstu.is.sbapp.patient.service.PatientService;
import ru.ulstu.is.sbapp.record.model.Record;
import ru.ulstu.is.sbapp.record.repository.RecordRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class RecordService {
    private EntityManager em;
    private final RecordRepository recordRepository;
    private final PatientRepository patientRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final PatientService patientService;
    private final MaintenanceService maintenanceService;
    private final ValidatorUtil validatorUtil;

    public RecordService(RecordRepository recordRepository, PatientRepository patientRepository,
                         MaintenanceRepository maintenanceRepository, PatientService patientService,
                         MaintenanceService maintenanceService, ValidatorUtil validatorUtil) {
        this.recordRepository = recordRepository;
        this.patientRepository = patientRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.patientService = patientService;
        this.maintenanceService = maintenanceService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Record addRecord(String recordDate, Patient patient, Maintenance maintenance) {
        final Record record = new Record(recordDate, patient, maintenance);
        validatorUtil.validate(record);
        return recordRepository.save(record);
    }

    @Transactional
    public Patient getByNamePatient(String str){return patientRepository.getFirstByFullName(str);}

    @Transactional
    public Maintenance getByNameMaintenance(String str){return maintenanceRepository.getFirstByNameMaintenance(str);}

    @Transactional(readOnly = true)
    public Record findRecord(Long id) {
        final Optional<Record> record = recordRepository.findById(id);
        return record.orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Transactional
    public List<Record> findAllRecords() {
        return recordRepository.findAll();
    }
    
    @Transactional
    public Record updateRecord(Long id, String recordDate, Patient patient, Maintenance maintenance) {
        if (recordDate == null) {
            throw new IllegalArgumentException("Record date is null or empty");
        }
        final Record currentRecord = findRecord(id);
        currentRecord.setDate(recordDate);
        currentRecord.setMaintenance(maintenance);
        currentRecord.setPatient(patient);
        validatorUtil.validate(currentRecord);
        return recordRepository.save(currentRecord);
    }

    @Transactional
    public Record deleteRecord(Long id) {
        final Record currentRecord = findRecord(id);
        recordRepository.delete(currentRecord);
        return currentRecord;
    }
}
