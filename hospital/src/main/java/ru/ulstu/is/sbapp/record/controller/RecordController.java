package ru.ulstu.is.sbapp.record.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.configuration.WebConfiguration;
import ru.ulstu.is.sbapp.patient.service.PatientService;
import ru.ulstu.is.sbapp.record.service.RecordService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/record")
public class RecordController {
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{id}")
    public RecordDTO getRecord(@PathVariable Long id) {
        return new RecordDTO(recordService.findRecord(id));
    }

    @GetMapping("/")
    public List<RecordDTO> getRecords() {
        return recordService.findAllRecords().stream()
                .map(RecordDTO::new)
                .toList();
    }


    @PostMapping("/edit")
    public RecordDTO createRecord(@RequestParam String recordDate,
                                  @RequestParam String patientName,
                                  @RequestParam String maintenanceName) {
        return new RecordDTO(recordService.addRecord(recordDate, recordService.getByNamePatient(patientName),
                recordService.getByNameMaintenance(maintenanceName)));
    }

    @PutMapping("/{id}")
    public RecordDTO updateRecord(@PathVariable Long id,
                                  @RequestParam String recordDate,
                                  @RequestParam String patientName,
                                  @RequestParam String maintenanceName) {
        return new RecordDTO(recordService.updateRecord(id, recordDate, recordService.getByNamePatient(patientName),
                recordService.getByNameMaintenance(maintenanceName)));
    }

    @DeleteMapping("/{id}")
    public RecordDTO deleteRecord(@PathVariable Long id) {
        return new RecordDTO(recordService.deleteRecord(id));
    }
}
