package com.administrationsystem.dentalClinic.modelTesting;

import com.administrationsystem.dentalClinic.models.patient.Patient;
import com.administrationsystem.dentalClinic.models.patient.PatientManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientManagerTest {

    private PatientManager patientManager = PatientManager.getInstance();

    @DisplayName("Junit test for updatePatient in PatientManager.")
    @Test
    public void PatientManager_updatePatient_ReturnsCorrectUpdatedObject() throws ParseException {

        //Arrange
        Patient patientData = new Patient();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("1990-01-01");
        patientData.setName("John");
        patientData.setSurname("Doe");
        patientData.setSsn("123456789");
        patientData.setBirthdate(date);
        patientData.setEmail("john@example.com");
        patientData.setTelephone("123456789");
        patientData.setAddress("123 Main St");
        patientData.setGender("Male");

        //Act
        Patient updatedPatient = patientManager.updatePatient(patientData);

        //Assert
        // Check that the updated patient has the same details
        assertEquals(patientData.getName(), updatedPatient.getName());
        assertEquals(patientData.getSurname(), updatedPatient.getSurname());
        assertEquals(patientData.getSsn(), updatedPatient.getSsn());
        assertEquals(patientData.getBirthdate(), updatedPatient.getBirthdate());
        assertEquals(patientData.getEmail(), updatedPatient.getEmail());
        assertEquals(patientData.getTelephone(), updatedPatient.getTelephone());
        assertEquals(patientData.getAddress(), updatedPatient.getAddress());
        assertEquals(patientData.getGender(), updatedPatient.getGender());
    }
}
