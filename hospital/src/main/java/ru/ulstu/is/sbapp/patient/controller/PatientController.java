package ru.ulstu.is.sbapp.patient.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.configuration.WebConfiguration;
import ru.ulstu.is.sbapp.patient.controller.PatientDTO;
import ru.ulstu.is.sbapp.patient.service.PatientService;
import ru.ulstu.is.sbapp.patient.controller.PatientDTO;
import ru.ulstu.is.sbapp.patient.service.PatientService;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/patient")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public PatientDTO getPatient(@PathVariable Long id) {
        return new PatientDTO(patientService.findPatient(id));
    }

    @GetMapping("/")
    public List<PatientDTO> getPatients() {
        return patientService.findAllPatients().stream()
                .map(PatientDTO::new)
                .toList();
    }


    @PostMapping("/edit")
    public PatientDTO createPatient(@RequestParam String patientFullName,
                                  @RequestParam String patientPhoneNumber,
                                  @RequestParam String patientEmail) {
        return new PatientDTO(patientService.addPatient(patientFullName, patientPhoneNumber, patientEmail));
    }

    @PutMapping("/patient/{id}")
    public PatientDTO updatePatient(@PathVariable Long id,
                                  @RequestParam String patientFullName,
                                  @RequestParam String patientPhoneNumber,
                                  @RequestParam String patientEmail) {
        return new PatientDTO(patientService.updatePatient(id, patientFullName, patientPhoneNumber, patientEmail));
    }


    @DeleteMapping("/{id}")
    public PatientDTO deletePatient(@PathVariable Long id) {
        return new PatientDTO(patientService.deletePatient(id));
    }
}
