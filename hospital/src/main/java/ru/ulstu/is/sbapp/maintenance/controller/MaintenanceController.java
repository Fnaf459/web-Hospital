package ru.ulstu.is.sbapp.maintenance.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.configuration.WebConfiguration;
import ru.ulstu.is.sbapp.maintenance.controller.MaintenanceDTO;
import ru.ulstu.is.sbapp.maintenance.model.Maintenance;
import ru.ulstu.is.sbapp.maintenance.service.MaintenanceService;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/maintenance")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/{id}")
    public MaintenanceDTO getMaintenance(@PathVariable Long id) {
        return new MaintenanceDTO(maintenanceService.findMaintenance(id));
    }

    @GetMapping("/")
    public List<MaintenanceDTO> getMaintenances() {
        return maintenanceService.findAllMaintenances().stream()
                .map(MaintenanceDTO::new)
                .toList();
    }


    @PostMapping("/edit")
    public MaintenanceDTO createMaintenance(@RequestParam String maintenanceName,
                                            @RequestParam int maintenanceCost,
                                            @RequestParam String doctorName) {
        return new MaintenanceDTO(maintenanceService.addMaintenance(maintenanceName, maintenanceCost, maintenanceService.getByNameDoctor(doctorName)));
    }

    @PutMapping("/{id}")
    public MaintenanceDTO updateMaintenance(@PathVariable Long id,
                                            @RequestParam String maintenanceFullName,
                                            @RequestParam int maintenanceCost,
                                            @RequestParam String doctorName) {
        return new MaintenanceDTO(maintenanceService.updateMaintenance(id, maintenanceFullName, maintenanceCost, maintenanceService.getByNameDoctor(doctorName)));
    }

    @DeleteMapping("/{id}")
    public MaintenanceDTO deleteMaintenance(@PathVariable Long id) {
        return new MaintenanceDTO(maintenanceService.deleteMaintenance(id));
    }
}
