package ru.ulstu.is.sbapp.patient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.patient.controller.PatientDTO;
import ru.ulstu.is.sbapp.patient.service.PatientService;
import ru.ulstu.is.sbapp.util.validation.ValidationException;

import javax.validation.Valid;

@Controller
@RequestMapping("/patient")
public class PatientMvcController {
    private final PatientService patientService;

    public PatientMvcController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String getPatients(Model model) {
        model.addAttribute("patients",
                patientService.findAllPatients().stream()
                        .map(PatientDTO::new)
                        .toList());
        return "patient";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editPatient(@PathVariable(required = false) Long id,
                             Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("patientDTO", new PatientDTO());
        } else {
            model.addAttribute("patientId", id);
            model.addAttribute("patientDTO", new PatientDTO(patientService.findPatient(id)));
            model.addAttribute("records", patientService.findPatient(id).getRecords());
        }
        return "patient-edit";
    }

    @GetMapping(value = {"/report"})
    public String getReport(Model model) {
        model.addAttribute("patients", patientService.findAllPatients().stream()
                .map(PatientDTO::new)
                .toList());
        return "report";
    }

    @PostMapping(value = {"", "/{id}"})
    public String savePatient(@PathVariable(required = false) Long id,
                             @ModelAttribute @Valid PatientDTO patientDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "patient-edit";
        }
        try {
            if (id == null || id <= 0) {
                patientService.addPatient(patientDTO.getFullName(), patientDTO.getPhoneNumber(), patientDTO.getEmail());
            } else {
                patientService.updatePatient(id, patientDTO.getFullName(), patientDTO.getPhoneNumber(), patientDTO.getEmail());
            }
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getMessage());
            return "patient-edit";
        }
        return "redirect:/patient";
    }

    @PostMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patient";
    }
}
