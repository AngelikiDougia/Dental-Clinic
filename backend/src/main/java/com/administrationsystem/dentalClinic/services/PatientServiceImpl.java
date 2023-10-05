package com.administrationsystem.dentalClinic.services;

import com.administrationsystem.dentalClinic.exceptions.ExistingPatientException;
import com.administrationsystem.dentalClinic.models.patient.Patient;
import com.administrationsystem.dentalClinic.models.patient.PatientManager;
import com.administrationsystem.dentalClinic.repositories.DentistRepository;
import com.administrationsystem.dentalClinic.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService
{
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DentistRepository dentistRepository;


    private PatientManager patientManager;

    @Override
    public Patient savePatient(Patient patient) {


        return patientRepository.save(patient);
    }

    public Patient savePatient(Patient patient, Long dentist_id) throws ExistingPatientException {

        if(existsBySsn(patient.getSsn())){
            throw new ExistingPatientException();
        }
        patient.setDentist(dentistRepository.findByDentistId(dentist_id));
        return savePatient(patient);

    }

    public String deletePatient(String ssn){

        if(!patientRepository.existsBySsn(ssn)){
            return "something went wrong";
        }
        patientRepository.deleteBySsn(ssn);
        return "Patient with ssn " + ssn + " has been deleted.";
    }

    @Override
    public Patient findBySsn(String ssn) {

        if(!patientRepository.existsBySsn(ssn)){
            return null;
        }
        return patientRepository.findBySsn(ssn);

    }

    @Override
    public Patient updatePatient(Patient patientData, Long dentist_id) throws ExistingPatientException {

        patientManager = PatientManager.getInstance();
        Patient updatedPatient = patientManager.updatePatient(patientData);
        deletePatient(patientData.getSsn());
        return savePatient(updatedPatient,dentist_id);
    }



    @Override
    public boolean existsBySsn(String ssnToCheck) {
        if (!patientRepository.existsBySsn(ssnToCheck)){
            return false;
        }
        return true;
    }


    @Override
    public List<Patient> getAllPatients(Long dentist_id) {
        return patientRepository.findByDentist_DentistId(dentist_id);
    }
}
