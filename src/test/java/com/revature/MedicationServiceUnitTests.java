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
    Medication medication3 = new Medication(1, "hello", 35, 2.9, Type.PILL, Status.IN_STOCK);

    @Test
    public void whenCreateMedicationIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> medicationService.createMedication(medication));
    }

    @Test
    public void whenCreateMedicationIsCalledReturnsCorrectMedicationObject() {
        Mockito.when(medicationService.createMedication(medication)).thenReturn(medication);
        Medication testMedication = medicationService.createMedication(medication);
        Assertions.assertEquals(testMedication, medication);
    }

    @Test
    public void whenCreateMedicationIsCalledWithNullObjectReturnsNull() {
        Medication testMedication = medicationService.createMedication(null);
        Assertions.assertNull(testMedication);
    }

    @Test
    public void whenGetAllMedicationIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(()-> medicationService.getAllMedication());
    }

}
