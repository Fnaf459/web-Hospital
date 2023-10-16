package ru.ulstu.is.sbapp.maintenance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.doctor.controller.DoctorDTO;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.doctor.service.DoctorService;
import ru.ulstu.is.sbapp.maintenance.controller.MaintenanceDTO;
import ru.ulstu.is.sbapp.maintenance.service.MaintenanceService;
import ru.ulstu.is.sbapp.user.model.UserRole;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceMvcController {
    private final MaintenanceService maintenanceService;
    private final DoctorService doctorService;

    public MaintenanceMvcController(MaintenanceService maintenanceService, DoctorService doctorService) {
        this.maintenanceService = maintenanceService;
        this.doctorService = doctorService;
    }

    @GetMapping
    public String getMaintenances(Model model) {
        model.addAttribute("maintenances",
                maintenanceService.findAllMaintenances().stream()
                        .map(MaintenanceDTO::new)
                        .toList());
        return "maintenance";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editMaintenance(@PathVariable(required = false) Long id,
                                  @ModelAttribute @Valid MaintenanceDTO maintenanceDTO,
                             Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("maintenanceDTO", new MaintenanceDTO());
        } else {
            model.addAttribute("maintenanceId", id);
            model.addAttribute("maintenanceDTO", new MaintenanceDTO(maintenanceService.findMaintenance(id)));
            model.addAttribute("doctors", maintenanceService.getByNameDoctor(maintenanceDTO.getDoctorFullName()));
        }
        return "maintenance-edit";
    }

    @GetMapping(value = {"/report"})
    public String getReport(Model model) {
        model.addAttribute("maintenances", maintenanceService.findAllMaintenances().stream()
                .map(MaintenanceDTO::new)
                .toList());
        return "report";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveMaintenance(@PathVariable(required = false) Long id,
                                  @ModelAttribute @Valid MaintenanceDTO maintenanceDTO,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "maintenance-edit";
        }
        if (id == null || id <= 0) {
            maintenanceService.addMaintenance(maintenanceDTO.getNameMaintenance(), maintenanceDTO.getCost(),  maintenanceService.getByNameDoctor(maintenanceDTO.getDoctorFullName()));
        } else {
            maintenanceService.updateMaintenance(id, maintenanceDTO.getNameMaintenance(), maintenanceDTO.getCost(), maintenanceService.getByNameDoctor(maintenanceDTO.getDoctorFullName()));
        }
        return "redirect:/maintenance";
    }

    @PostMapping("/delete/{id}")
    public String deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return "redirect:/maintenance";
    }
}
