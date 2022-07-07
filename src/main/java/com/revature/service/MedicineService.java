package com.revature.service;

import com.revature.model.Medicine;
import com.revature.model.enums.Status;
import com.revature.model.enums.Type;
import com.revature.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {
    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository){
        this.medicineRepository = medicineRepository;
    }

    public Medicine createMedicine(Medicine medicine){
        return medicineRepository.save(medicine);
    }

    public List<Medicine> getAllMedicine(){
        return medicineRepository.findAll();
    }

    public Medicine getMedicineById(int id){
        return medicineRepository.findById(id).orElseThrow( () -> new RuntimeException("Medicine could not be found"));
    }

    public Medicine updateMedicine(Medicine medicine){
        Medicine medicineToEdit = medicineRepository.findById(medicine.getId()).orElseThrow( () -> new RuntimeException("Medicine could not be found"));
        medicineToEdit.setName(medicine.getName());
        medicineToEdit.setStock(medicine.getStock());
        medicineToEdit.setPrice(medicine.getPrice());
        medicineToEdit.setType(medicine.getType());
        medicineToEdit.setStatus(medicine.getStatus());
        return medicineRepository.save(medicineToEdit);
    }

    public void deleteMedicine(Medicine medicine){
        Medicine medicineIdEntered = medicineRepository.findById(medicine.getId()).orElseThrow( () -> new RuntimeException("Medicine could not be found"));
        medicineRepository.delete(medicineIdEntered);
    }

    public List<Type> getAllByType(Type type){
        return medicineRepository.getAllByType(type);
    }

    public List<Status> getAllByStatus(Status status){
        return medicineRepository.getAllByStatus(status);
    }
}
