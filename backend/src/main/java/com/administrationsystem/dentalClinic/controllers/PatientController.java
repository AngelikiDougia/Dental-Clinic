package com.administrationsystem.dentalClinic.controllers;


import com.administrationsystem.dentalClinic.models.patient.Patient;


import com.administrationsystem.dentalClinic.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/patients")
public class PatientController
{

    @Autowired
    private PatientService patientService;


    @PostMapping("/addPatient")
    public ResponseEntity<String> addPatient(@RequestBody Patient patient, @RequestParam Long dentistId){

        try {

            patientService.savePatient(patient, dentistId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Resource saved successfully");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }

    }


    @GetMapping("/getAllPatients/{dentist_id}")
    public List<Patient> getAllPatients(@PathVariable("dentist_id") Long dentist_id)
    {


        return patientService.getAllPatients(dentist_id);
    }

    @DeleteMapping("/deletePatient/{ssn}")
    public String deletePatient(@PathVariable String ssn)
    {


        patientService.deletePatient(ssn);
        return  "Patient with ssn "+ ssn +" has been deleted success.";
    }


    @GetMapping("/getPatient/{ssn}")
    public ResponseEntity<Patient> getPatientBySsn(@PathVariable String ssn)
    {
        Patient patientProfile = patientService.findBySsn(ssn);
        if (patientProfile!=null) {
            Patient patient = patientProfile;

            return ResponseEntity.ok(patient);
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/updatePatient/{ssn}")
    public Patient updatePatient(@RequestBody Patient newPatient, @PathVariable String ssn,@RequestParam Long dentistId) {

        if(patientService.findBySsn(ssn)!=null){
            patientService.deletePatient(ssn);
            return patientService.updatePatient(newPatient, dentistId);
        }

        return null;



    }

}
