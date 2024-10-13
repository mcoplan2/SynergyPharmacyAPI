package com.revature;

import com.revature.model.Medication;
import com.revature.model.enums.Status;
import com.revature.model.enums.Type;
import com.revature.repository.MedicationRepository;
import com.revature.service.MedicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicationServiceUnitTests {

    private MedicationService medicationService;
    private MedicationRepository medicationRepository;

    @BeforeEach
    public void setup() {
        medicationRepository = Mockito.mock(MedicationRepository.class);
        medicationService = new MedicationService(medicationRepository);
    }

    Medication medication = new Medication(1, "hello", 30, 2.9, Type.PILL, Status.IN_STOCK);
    Medication medication2 = new Medication(1, "hello", 60, 2.9, Type.PILL, Status.OUT_OF_STOCK);

    @Test
    public void whenCreateMedicationIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> medicationService.createMedication(medication));
    }

    @Test
    public void whenCreateMedicationIsCalledReturnsCorrectMedicationObject() {
        Mockito.when(medicationRepository.save(medication)).thenReturn(medication);
        Medication testMedication = medicationService.createMedication(medication);
        Assertions.assertEquals(testMedication.getName(), medication.getName());
    }

    @Test
    public void whenCreateMedicationIsCalledWithDuplicateMedicationThrowsError() {
        Mockito.when(medicationRepository.findByName(medication.getName())).thenReturn(Optional.ofNullable(medication));
        Assertions.assertThrows(RuntimeException.class, () -> medicationService.createMedication(medication));
    }

    @Test
    public void whenCreateMedicationIsCalledWithUniqueMedicationNameDoesNotThrow() {
        Mockito.when(medicationRepository.findByName(medication.getName())).thenReturn(Optional.empty());
        Mockito.when(medicationRepository.save(medication)).thenReturn(medication);
        Assertions.assertDoesNotThrow(() -> medicationService.createMedication(medication));
    }

    @Test
    public void whenGetAllMedicationIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(()-> medicationService.getAllMedication());
    }

    @Test
    public void whenGetAllMedicationIsCalledReturnsCorrectList() {
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication);
        medicationList.add(medication2);
        Mockito.when(medicationRepository.findAll()).thenReturn(medicationList);
        List<Medication> actualList = medicationService.getAllMedication();
        Assertions.assertEquals(medicationList,actualList);
    }

    @Test
    public void whenGetMedicationByIdIsCalledDoesNotThrowException() {
        Mockito.when(medicationRepository.findById(1)).thenReturn(Optional.ofNullable(medication2));
        Assertions.assertDoesNotThrow(()-> medicationService.getMedicationById(1));
    }

    @Test
    public void whenGetMedicationByIdIsCalledReturnsMedication() {
        Mockito.when(medicationRepository.findById(1)).thenReturn(Optional.ofNullable(medication2));
        Medication actualMedication = medicationService.getMedicationById(1);
        Assertions.assertEquals(medication2,actualMedication);
    }

    @Test
    public void whenUpdateMedicatedIsCalledDoesNotThrowException() {
        Mockito.when(medicationRepository.findById(1)).thenReturn(Optional.ofNullable(medication2));
        Assertions.assertDoesNotThrow(()-> medicationService.updateMedication(medication));
    }

    @Test
    public void whenUpdateMedicatedIsCalledReturnsCorrectMedication() {
        Mockito.when(medicationRepository.findById(1)).thenReturn(Optional.ofNullable(medication2));
        Mockito.when(medicationRepository.save(medication2)).thenReturn(medication);
        Medication updatedMedication = medicationService.updateMedication(medication);
        Assertions.assertEquals(updatedMedication, medication);
    }

    @Test
    public void whenDeleteMedicationIsRunDoesNotThrowException() {
        Mockito.when(medicationRepository.findById(1)).thenReturn(Optional.ofNullable(medication2));
        Assertions.assertDoesNotThrow(()-> medicationService.deleteMedication(medication));
    }

    @Test
    public void whenGetMedicationByLetterIsCalledDoesNothThrowException() {
        Assertions.assertDoesNotThrow(() -> medicationService.getMedicationByLetter("l"));
    }

    @Test
    public void whenGetMedicationByLetterIsCalledReturnsCorrectMedication() {
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication);
        medicationList.add(medication2);
        Mockito.when(medicationRepository.findByNameStartingWith("l")).thenReturn(medicationList);
        List<Medication> actualMedicationList = medicationService.getMedicationByLetter("l");
        Assertions.assertEquals(actualMedicationList, medicationList);
    }

    @Test
    public void whenGetMedicationContainingIsCalledDoesNothThrowException() {
        Assertions.assertDoesNotThrow(() -> medicationService.getMedicationContaining("ls"));
    }

    @Test
    public void whenGetMedicationContainingIsCalledReturnsCorrectMedication() {
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication);
        medicationList.add(medication2);
        Mockito.when(medicationRepository.findByNameContainingIgnoreCase("ls")).thenReturn(medicationList);
        List<Medication> actualMedicationList = medicationService.getMedicationContaining("ls");
        Assertions.assertEquals(actualMedicationList, medicationList);
    }

    @Test
    public void whenGetAllByTypeIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(()-> medicationService.getAllByType(Type.PILL));
    }

    @Test
    public void whenGetAllByTypeIsCalledReturnsCorrectMedication() {
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication);
        medicationList.add(medication2);
        Mockito.when(medicationRepository.getAllByType(Type.PILL)).thenReturn(medicationList);
        List<Medication> actualMedicationList = medicationService.getAllByType(Type.PILL);
        Assertions.assertEquals(actualMedicationList, medicationList);
    }

    @Test
    public void whenGetAllByStatusIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(()-> medicationService.getAllByStatus(Status.RUNNING_LOW));
    }

    @Test
    public void whenGetAllByStatusIsCalledReturnsCorrectMedication() {
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication);
        medicationList.add(medication2);
        Mockito.when(medicationRepository.getAllByStatus(Status.RUNNING_LOW)).thenReturn(medicationList);
        List<Medication> actualMedicationList = medicationService.getAllByStatus(Status.RUNNING_LOW);
        Assertions.assertEquals(actualMedicationList, medicationList);
    }



}
