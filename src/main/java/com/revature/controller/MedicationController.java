package com.revature.controller;

import com.revature.model.Medication;
import com.revature.model.enums.Status;
import com.revature.model.enums.Type;
import com.revature.service.MedicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/medications")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService){
        this.medicationService = medicationService;
    }

    @PostMapping
    public Medication createMedication(@RequestBody Medication medication){
        return medicationService.createMedication(medication);
    }

    @GetMapping
    @CrossOrigin
    public List<Medication> getAllMedication(){
        return medicationService.getAllMedication();
    }

    @GetMapping("/{id}")
    public Medication getMedicationById(@PathVariable int id){
        return medicationService.getMedicationById(id);
    }

    @GetMapping("/status/{Status}")
    public List<Medication> getAllByStatus(@PathVariable("Status") String status){
        Status statusId = Status.valueOf(status.toUpperCase(Locale.ROOT));
        return medicationService.getAllByStatus(statusId);
    }

    @GetMapping("/type/{Type}")
        public List<Medication> getAllByType(@PathVariable("Type") String type){
        Type typeId = Type.valueOf(type.toUpperCase(Locale.ROOT));
        return medicationService.getAllByType(typeId);

    }

    @GetMapping("/filter")
    public List<Medication> getAllMedicationsByFirstLetter(@RequestParam(required = false) String letter, String query) {
        if (letter != null && !letter.isEmpty()) {
            if (query != null && !query.isEmpty()) {
                return medicationService.getMedicationContaining(query);
            } else {
                return medicationService.getMedicationByLetter(letter.toUpperCase());
            }
        } else {
            if(query != null && !query.isEmpty()) {
                return medicationService.getMedicationContaining(query);
            } else {
            return medicationService.getAllMedication();
        }
    }
        }

    @PutMapping
    public Medication updateMedication(@RequestBody Medication medication){
        return medicationService.updateMedication(medication);
    }

    @DeleteMapping
    public void deleteMedication(@RequestBody Medication medication){
        medicationService.deleteMedication(medication);
    }

}
