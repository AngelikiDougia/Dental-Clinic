package com.administrationsystem.dentalClinic.repositoryTesting;

import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.models.patient.Patient;
import com.administrationsystem.dentalClinic.repositories.DentistRepository;
import com.administrationsystem.dentalClinic.repositories.PatientRepository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;



@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DentistRepository dentistRepository;


    @DisplayName("JUnit Test for findBySsn in PatientRepository")
    @Test
    public void PatientRepository_FindBySsn_ReturnSavedPatient() {
        // Arrange
        String ssnToFind = "123456789";
        Patient expectedPatient = new Patient();
        expectedPatient.setSsn(ssnToFind);


        // Act
        patientRepository.save(expectedPatient);
        Patient foundPatient = patientRepository.findBySsn(ssnToFind);

        // Assert
        assertThat(foundPatient).isNotNull();
        assertEquals(ssnToFind, foundPatient.getSsn());
    }

    @DisplayName("JUnit Test for existsBySsn in PatientRepository")
    @Test
    public void PatientRepository_ExistsBySsn_ReturnCorrectBooleanValue() {
        // Arrange
        String ssnToCheck = "123456789";
        Patient expectedPatient = new Patient();
        expectedPatient.setSsn(ssnToCheck);

        String ssn2ToCheck = "987654321";
        Patient expectedPatient2 = new Patient();
        expectedPatient2.setSsn(ssn2ToCheck);
        // Act
        patientRepository.save(expectedPatient);



        // Assert
        assertTrue(patientRepository.existsBySsn(ssnToCheck));
        assertFalse(patientRepository.existsBySsn(ssn2ToCheck));
    }

    @DisplayName("JUnit Test for deleteBySsn in PatientRepository")
    @Test
    @Transactional
    public void PatientRepository_DeleteBySsn_NotReturnDeletedPatient() {
        // Arrange
        String ssnToDelete = "123456789";
        Patient expectedPatient = new Patient();
        expectedPatient.setSsn(ssnToDelete);

        String ssnToExist = "987654321";
        Patient expectedPatient2 = new Patient();
        expectedPatient2.setSsn(ssnToExist);

        // Act
        patientRepository.save(expectedPatient);
        patientRepository.save(expectedPatient2);
        patientRepository.deleteBySsn(ssnToDelete);

        // Assert
        assertThat(patientRepository.findBySsn(ssnToDelete)).isNull();
        assertThat(patientRepository.findBySsn(ssnToExist)).isNotNull();

    }


    @DisplayName("JUnit Test for findByDentist_DentistId in PatientRepository")
    @Test

    public void PatientRepository_FindByDentistId_ReturnCorrectedPatientLists() {
        // Arrange

        Dentist dentist = new Dentist();
        dentistRepository.save(dentist);
        Patient patient1 = new Patient();
        patient1.setDentist(dentist);
        patient1.setSsn("12345");

        Patient patient2 = new Patient();
        patient2.setDentist(dentist);
        patient2.setSsn("98765");

        patientRepository.save(patient1);
        patientRepository.save(patient2);

        // Act
        List<Patient> foundPatients = patientRepository.findByDentist_DentistId(dentist.getDentistId());

        // Assert
        assertNotNull(foundPatients);
        assertEquals(2, foundPatients.size());


    }

}
