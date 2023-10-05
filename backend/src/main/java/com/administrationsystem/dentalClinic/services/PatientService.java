package com.administrationsystem.dentalClinic.services;

import com.administrationsystem.dentalClinic.exceptions.ExistingPatientException;
import com.administrationsystem.dentalClinic.models.patient.Patient;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface PatientService
{

    public Patient savePatient(Patient patient);

    public Patient savePatient(Patient patient, Long dentist_id) throws ExistingPatientException;

    public List<Patient> getAllPatients(Long dentist_id);

    public String deletePatient(String id);

    Patient findBySsn(String ssn);

    Patient updatePatient(Patient patientData, Long dentist_id) throws ExistingPatientException;


    boolean existsBySsn(String ssnToCheck);
}
