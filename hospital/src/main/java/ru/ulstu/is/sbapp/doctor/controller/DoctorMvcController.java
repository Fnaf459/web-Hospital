package ru.ulstu.is.sbapp.doctor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.doctor.service.DoctorService;
import ru.ulstu.is.sbapp.util.validation.ValidationException;

import javax.validation.Valid;

@Controller
@RequestMapping("/doctor")
public class DoctorMvcController {
    private final DoctorService doctorService;

    public DoctorMvcController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String getDoctors(Model model) {
        model.addAttribute("doctors",
                doctorService.findAllDoctors().stream()
                        .map(DoctorDTO::new)
                        .toList());
        return "doctor";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editDoctor(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("doctorDTO", new DoctorDTO());
        } else {
            model.addAttribute("doctorId", id);
            model.addAttribute("doctorDTO", new DoctorDTO(doctorService.findDoctor(id)));
            model.addAttribute("maintenances", doctorService.findDoctor(id).getMaintenances());
            model.addAttribute("reviews", doctorService.findDoctor(id).getReviews());
        }
        return "doctor-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveDoctor(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid DoctorDTO doctorDTO,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "doctor-edit";
        }
        try {
            if (id == null || id <= 0) {
                doctorService.addDoctor(doctorDTO.getFullName(), doctorDTO.getPhoneNumber(), doctorDTO.getEmail());
            } else {
                doctorService.updateDoctor(id, doctorDTO.getFullName(), doctorDTO.getPhoneNumber(), doctorDTO.getEmail());
            }
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getMessage());
            return "doctor-edit";
        }
        return "redirect:/doctor";
    }

    @PostMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctor";
    }
}
