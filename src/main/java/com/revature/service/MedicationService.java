package com.revature.service;

import com.revature.model.Medication;
import com.revature.model.enums.Status;
import com.revature.model.enums.Type;
import com.revature.repository.MedicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {
    private final MedicationRepository medicationRepository;

    public MedicationService(MedicationRepository medicationRepository){
        this.medicationRepository = medicationRepository;
    }

    public Medication createMedication(Medication medication) {
        Optional<Medication> existingMedication = medicationRepository.findByName(medication.getName());

        if(!existingMedication.isPresent()) {
            return medicationRepository.save(medication);
        }

        throw new RuntimeException(medication.getName()+" exists already");
    }

    public List<Medication> getAllMedication(){
        return medicationRepository.findAll();
    }

    public Medication getMedicationById(int id){
        
        return medicationRepository.findById(id).orElseThrow( () -> new RuntimeException("Medication could not be found"));
    }

    public Medication updateMedication(Medication medication){
        Medication medicationToEdit = medicationRepository.findById(medication.getId()).orElseThrow( () -> new RuntimeException("Medication could not be found"));
        medicationToEdit.setName(medication.getName());
        medicationToEdit.setStock(medication.getStock());
        medicationToEdit.setPrice(medication.getPrice());
        medicationToEdit.setType(medication.getType());
        medicationToEdit.setStatus(medication.getStatus());
        return medicationRepository.save(medicationToEdit);
    }

    public void deleteMedication(Medication medication){
        Medication medicationIdEntered = medicationRepository.findById(medication.getId()).orElseThrow( () -> new RuntimeException("Medication could not be found"));
        medicationRepository.delete(medicationIdEntered);
    }

    public List<Medication> getMedicationByLetter(String letter) {
        return medicationRepository.findByNameStartingWith(letter);
    }

    public List<Medication> getMedicationContaining(String query) {
        return medicationRepository.findByNameContainingIgnoreCase(query);
    }

    public List<Medication> getAllByType(Type type){
        return medicationRepository.getAllByType(type);
    }

    public List<Medication> getAllByStatus(Status status){
        return medicationRepository.getAllByStatus(status);
    }
}
