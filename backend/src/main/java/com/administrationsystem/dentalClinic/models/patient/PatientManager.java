package com.administrationsystem.dentalClinic.models.patient;

import java.util.ArrayList;
import java.util.Arrays;

public class PatientManager
{


    private static PatientManager patientManager;

    private ArrayList<String> details;

    public PatientManager(){
        this.details = new ArrayList<>(Arrays.asList("name","surname","ssn","birthdate","email","telephone","address","gender"));

    }

    public Patient updatePatient(Patient patientData)
    {
        Patient patient = new Patient();

        for(String detail: details){
            setDetail(detail,patient,patientData);
        }
        return patient;

    }

    public void setDetail(String detail,Patient patient,Patient PatientData)
    {

        switch (detail)
        {
            case "name" -> patient.setName(PatientData.getName());
            case "surname" -> patient.setSurname(PatientData.getSurname());
            case "ssn" -> patient.setSsn(PatientData.getSsn());
            case "birthdate" -> patient.setBirthdate(PatientData.getBirthdate());
            case "email" -> patient.setEmail(PatientData.getEmail());
            case "telephone" -> patient.setTelephone(PatientData.getTelephone());
            case "address" -> patient.setAddress(PatientData.getAddress());
            case "gender" -> patient.setGender(PatientData.getGender());
        }
    }

    public static PatientManager getInstance() {
        if (patientManager == null) {
            patientManager = new PatientManager();
        }
        return patientManager;
    }





}
