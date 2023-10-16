package ru.ulstu.is.sbapp.patient.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.patient.model.Patient;
import ru.ulstu.is.sbapp.patient.repository.PatientRepository;
import ru.ulstu.is.sbapp.record.model.Record;
import ru.ulstu.is.sbapp.record.repository.RecordRepository;
import ru.ulstu.is.sbapp.record.service.RecordNotFoundException;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @PersistenceContext
    private EntityManager em;
    private final PatientRepository patientRepository;
    private final RecordRepository recordRepository;
    private final ValidatorUtil validatorUtil;

    public PatientService(PatientRepository patientRepository, RecordRepository recordRepository,  ValidatorUtil validatorUtil) {
        this.recordRepository = recordRepository;
        this.patientRepository = patientRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Patient addPatient(String patientFullName, String patientPhoneNumber, String patientEmail) {
        final Patient patient = new Patient(patientFullName, patientPhoneNumber, patientEmail);
        validatorUtil.validate(patient);
        return patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public Patient findPatient(Long id) {
        final Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElseThrow(() -> new PatientNotFoundException(id));
    }

    @Transactional
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    @Transactional
    public Record findRecord(Long id) {
        final Optional<Record> record = recordRepository.findById(id);
        return record.orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Transactional
    public Patient updatePatient(Long id, String patientFullName, String patientPhoneNumber, String patientEmail) {
        if (!StringUtils.hasText(patientFullName)
                && !StringUtils.hasText(patientPhoneNumber)
                && !StringUtils.hasText(patientEmail)) {
            throw new IllegalArgumentException("Patient name, phone number and email is null or empty");
        }
        final Patient currentPatient = findPatient(id);
        currentPatient.setFullName(patientFullName);
        currentPatient.setPhoneNumber(patientPhoneNumber);
        currentPatient.setEmail(patientEmail);
        validatorUtil.validate(currentPatient);
        return patientRepository.save(currentPatient);
    }

    @Transactional
    public Patient deletePatient(Long id) {
        final Patient currentPatient = findPatient(id);
        patientRepository.delete(currentPatient);
        return currentPatient;
    }
}
