package com.revature;

import com.revature.model.Medicine;
import com.revature.model.enums.Status;
import com.revature.model.enums.Type;
import com.revature.repository.MedicineRepository;
import com.revature.service.MedicineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MedicineServiceUnitTests {

    private MedicineService medicineService;
    private MedicineRepository medicineRepository;

    @BeforeEach
    public void setup() {
        medicineRepository = Mockito.mock(MedicineRepository.class);
        medicineService = new MedicineService(medicineRepository);
    }

    Medicine medicine = new Medicine(1, "hello", 30, 2.9, Type.PILL, Status.IN_STOCK);
    Medicine medicine2 = new Medicine(1, "hello", 60, 2.9, Type.PILL, Status.OUT_OF_STOCK);
    Medicine medicine3 = new Medicine(1, "hello", 35, 2.9, Type.PILL, Status.IN_STOCK);

    @Test
    public void whenCreateMedicineIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> medicineService.createMedicine(medicine));
    }

    @Test
    public void whenCreateMedicineIsCalledReturnsCorrectMedicineObject() {
        Mockito.when(medicineService.createMedicine(medicine)).thenReturn(medicine);
        Medicine testMedicine = medicineService.createMedicine(medicine);
        Assertions.assertEquals(testMedicine, medicine);
    }

    @Test
    public void whenGetAllMedicineIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(()-> medicineService.getAllMedicine());
    }

}
