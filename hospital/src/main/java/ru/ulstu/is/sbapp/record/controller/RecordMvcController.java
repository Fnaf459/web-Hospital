package ru.ulstu.is.sbapp.record.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.record.service.RecordService;

import javax.validation.Valid;

@Controller
@RequestMapping("/record")
public class RecordMvcController {
    private final RecordService recordService;

    public RecordMvcController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    public String getRecords(Model model) {
        model.addAttribute("records",
                recordService.findAllRecords().stream()
                        .map(RecordDTO::new)
                        .toList());
        return "record";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editRecord(@PathVariable(required = false) Long id,
                             Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("recordDTO", new RecordDTO());
        } else {
            model.addAttribute("recordId", id);
            model.addAttribute("recordDTO", new RecordDTO(recordService.findRecord(id)));
        }
        return "record-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveRecord(@PathVariable(required = false) Long id,
                             @ModelAttribute @Valid RecordDTO recordDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "record-edit";
        }
        if (id == null || id <= 0) {
            recordService.addRecord(recordDTO.getDate(), recordService.getByNamePatient(recordDTO.getPatientName()),
                    recordService.getByNameMaintenance(recordDTO.getMaintenanceName()));
        } else {
            recordService.updateRecord(id, recordDTO.getDate(),recordService.getByNamePatient(recordDTO.getPatientName()),
                    recordService.getByNameMaintenance(recordDTO.getMaintenanceName()));
        }
        return "redirect:/record";
    }

    @PostMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return "redirect:/record";
    }
}
