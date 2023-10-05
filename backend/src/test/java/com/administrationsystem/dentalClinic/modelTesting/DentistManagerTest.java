package com.administrationsystem.dentalClinic.modelTesting;

import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.models.dentist.DentistManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DentistManagerTest{


    private DentistManager dentistManager = DentistManager.getInstance();

    private ArrayList<String> detailsDentist;



    @DisplayName("Junit test for createDentist in DentistManager.")
    @Test
    public void DentistManager_createDentist_returnsRightCreatedObject() {
        Dentist dentistData = new Dentist();
        dentistData.setName("John");
        dentistData.setSurname("Doe");
        dentistData.setUsername("johndoe");
        dentistData.setEmail("john@example.com");
        dentistData.setDentistId(1L);
        dentistData.setTelephone("123456789");
        dentistData.setAddress("123 Main St");

        Dentist newDentist = dentistManager.createDentist(dentistData);

        // Check that the created dentist has the same details
        assertEquals(dentistData.getName(), newDentist.getName());
        assertEquals(dentistData.getSurname(), newDentist.getSurname());
        assertEquals(dentistData.getUsername(), newDentist.getUsername());
        assertEquals(dentistData.getEmail(), newDentist.getEmail());
        assertEquals(dentistData.getDentistId(), newDentist.getDentistId());
        assertEquals(dentistData.getTelephone(), newDentist.getTelephone());
        assertEquals(dentistData.getAddress(), newDentist.getAddress());
    }

    @DisplayName("JUnit test for updateDentist in DentistManager.")
    @Test
    public void DentistManager_updateDentist_returnsCorrectlyUpdatedObject() {
        //Arrange
        Dentist oldDentist = new Dentist();
        oldDentist.setName("John");
        oldDentist.setSurname("Doe");
        oldDentist.setUsername("johndoe");
        oldDentist.setDentistId(1L);
        oldDentist.setEmail("john@example.com");
        oldDentist.setTelephone("123456789");
        oldDentist.setAddress("123 Main St");

        Dentist newDentist = new Dentist();
        newDentist.setName("UpdatedName");
        newDentist.setSurname("UpdatedSurname");
        newDentist.setUsername("updatedusername");
        newDentist.setEmail("updated@example.com");

        newDentist.setTelephone("987654321");
        newDentist.setAddress("456 Updated St");

        //Act
        Dentist updatedDentist = dentistManager.updateDentist(oldDentist, newDentist);

        // Assert
        assertEquals(newDentist.getName(), updatedDentist.getName());
        assertEquals(newDentist.getSurname(), updatedDentist.getSurname());
        assertEquals(newDentist.getUsername(), updatedDentist.getUsername());
        assertEquals(newDentist.getEmail(), updatedDentist.getEmail());

        assertEquals(newDentist.getTelephone(), updatedDentist.getTelephone());
        assertEquals(newDentist.getAddress(), updatedDentist.getAddress());
    }

}
