package ru.ulstu.is.sbapp.doctor.controller;

import org.apache.catalina.startup.Tomcat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.configuration.WebConfiguration;
import ru.ulstu.is.sbapp.doctor.service.DoctorService;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{id}")
    public DoctorDTO getDoctor(@PathVariable Long id) {
        return new DoctorDTO(doctorService.findDoctor(id));
    }

    @GetMapping("/")
    public List<DoctorDTO> getDoctors() {
        return doctorService.findAllDoctors().stream()
                .map(DoctorDTO::new)
                .toList();
    }

    @PostMapping("/edit")
    public DoctorDTO createDoctor(@RequestParam String doctorFullName,
                                  @RequestParam String doctorPhoneNumber,
                                  @RequestParam String doctorEmail) {
        return new DoctorDTO(doctorService.addDoctor(doctorFullName, doctorPhoneNumber, doctorEmail));
    }

    @PutMapping("/doctor/{id}")
    public DoctorDTO updateDoctor(@PathVariable Long id,
                                  @RequestParam String doctorFullName,
                                  @RequestParam String doctorPhoneNumber,
                                  @RequestParam String doctorEmail) {
        return new DoctorDTO(doctorService.updateDoctor(id, doctorFullName, doctorPhoneNumber, doctorEmail));
    }

    @DeleteMapping("/{id}")
    public DoctorDTO deleteDoctor(@PathVariable Long id) {
        return new DoctorDTO(doctorService.deleteDoctor(id));
    }
}
