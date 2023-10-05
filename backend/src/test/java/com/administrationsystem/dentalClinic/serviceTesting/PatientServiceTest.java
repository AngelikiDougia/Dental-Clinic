package com.administrationsystem.dentalClinic.serviceTesting;


import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.models.patient.Patient;
import com.administrationsystem.dentalClinic.models.patient.PatientManager;
import com.administrationsystem.dentalClinic.repositories.DentistRepository;
import com.administrationsystem.dentalClinic.repositories.PatientRepository;
import com.administrationsystem.dentalClinic.services.PatientServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest{


    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DentistRepository dentistRepository;

    @Mock
    private PatientManager patientManager;

    @InjectMocks
    private PatientServiceImpl patientService;





    @DisplayName("JUnit test for savePatient in PatientService ")
    @Test
    public void PatientService_savePatient_ReturnsPatientCorrectObject()
    {
        //Arrange
        Patient expectedPatient = new Patient();
        expectedPatient.setSsn("3456789");
        expectedPatient.setName("Maria");
        when(patientRepository.save(expectedPatient)).thenReturn(expectedPatient);

        //Act
        Patient actualPatient = patientService.savePatient(expectedPatient);

        //Assert
        assertThat(actualPatient).isNotNull();
        assertEquals(expectedPatient.getSsn(),actualPatient.getSsn());
        assertEquals(expectedPatient.getName(),actualPatient.getName());

    }

    @DisplayName("JUnit test for savePatient with parameter DentistId in PatientService")
    @Test
    public void PatientService_SavePatientWithDentistId_thenReturnPatientCorrectObject()
    {
        //Arrange
        Patient expectedPatient = new Patient();
        expectedPatient.setSsn("3456789");
        expectedPatient.setName("Maria");

        Long dentist_id = 12L;
        Dentist dentist = new Dentist();
        dentist.setDentistId(dentist_id);
        when(dentistRepository.findByDentistId(dentist_id)).thenReturn(dentist);
        when(patientRepository.save(expectedPatient)).thenReturn(expectedPatient);

        //Act
        Patient actualPatient = patientService.savePatient(expectedPatient,dentist_id);

        //Assert
        assertThat(actualPatient).isNotNull();
        assertEquals(expectedPatient.getSsn(),actualPatient.getSsn());
        assertEquals(expectedPatient.getName(),actualPatient.getName());
        assertEquals(dentist_id, actualPatient.getDentist().getDentistId());

    }

    @DisplayName("JUnit test for deletePatient method in PatientService")
    @Test
    public void PatientService_deletePatient_ReturnsNothing(){
        // Arrange
        String ssn = "987654321";

        when(patientRepository.existsBySsn(ssn)).thenReturn(true);
        willDoNothing().given(patientRepository).deleteBySsn(ssn);

        //Act
        patientService.deletePatient(ssn);

        // Assert
        verify(patientRepository, times(1)).deleteBySsn(ssn);

    }

    @DisplayName("JUnit test for findBySsn method in PatientService")
    @Test
    public void PatientService_findBySsn_ReturnsCorrectPatientObject()
    {
        //Arrange
        String ssnToCheck = "123456789";
        Patient expectedPatient = new Patient();
        expectedPatient.setName("Maria");
        expectedPatient.setSurname("Ioannou");
        expectedPatient.setEmail("mariaioannou@gmail.com");
        when(patientRepository.existsBySsn(ssnToCheck)).thenReturn(true);
        when(patientRepository.findBySsn(ssnToCheck)).thenReturn(expectedPatient);
        //Act

        Patient actualPatient = patientService.findBySsn(ssnToCheck);

        //Assert
        assertNotNull(actualPatient);
        assertEquals(expectedPatient.getName(), actualPatient.getName());
        assertEquals(expectedPatient.getSurname(), actualPatient.getSurname());
        assertEquals(expectedPatient.getEmail(), actualPatient.getEmail());
    }

    @DisplayName("JUnit test for existsBySsn method in PatientService")
    @Test
    public void PatientService_existsBySsn_thenReturnCorrectBooleanValue()
    {
        //Arrange
        Boolean actualValue;
        String ssn = "1234568";
        when(patientRepository.existsBySsn(ssn)).thenReturn(true);

        //Act
        actualValue = patientService.existsBySsn(ssn);

        //Assert
        assertTrue(actualValue);

    }

    @DisplayName("JUnit test for getAllPatients in PatientService")
    @Test
    public void PatientService_getAllPatients_willReturnPatientList()
    {
        //Arrange
        String [] names = {"Maria","Georgios"};
        String [] surnames = {"Ioannou","Georgiou"};
        List<Patient> expectedPatients = new ArrayList<>();
        for(int i=0; i<2; i++){
            Patient patient = new Patient();
            patient.setName(names[i]);
            patient.setSurname(surnames[i]);
            expectedPatients.add(patient);
        }
        when(patientRepository.findByDentist_DentistId(1L)).thenReturn(expectedPatients);

        //Act
        List<Patient> actualPatients = patientService.getAllPatients(1L);

        //Assert
        assertEquals(expectedPatients.size(),actualPatients.size());
        assertEquals(expectedPatients.get(0).getName(),actualPatients.get(0).getName());
        assertEquals(expectedPatients.get(1).getSurname(),actualPatients.get(1).getSurname());



    }





}
