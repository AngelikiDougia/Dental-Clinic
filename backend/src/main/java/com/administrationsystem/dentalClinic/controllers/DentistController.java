package com.administrationsystem.dentalClinic.controllers;

import com.administrationsystem.dentalClinic.dao.LoginDao;

import com.administrationsystem.dentalClinic.exceptions.AppointmentOverlapException;
import com.administrationsystem.dentalClinic.models.appointment.Appointment;
import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.models.patient.Patient;
import com.administrationsystem.dentalClinic.response.LoginResponse;
import com.administrationsystem.dentalClinic.services.AppointmentService;
import com.administrationsystem.dentalClinic.services.DentistService;

import com.administrationsystem.dentalClinic.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/")
public class DentistController
{

    @Autowired
    private DentistService dentistService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;



    @PostMapping("/login")
    public ResponseEntity<?> loginDentist(@RequestBody LoginDao loginDao){
        LoginResponse loginResponse =dentistService.loginDentist(loginDao);
        return ResponseEntity.ok(loginResponse);
    }


    @PostMapping("/createDentist")
    public ResponseEntity<String> addDentist(@RequestBody Dentist dentist){

        try {
            //System.out.println(dentist.getPassword());
            dentistService.saveDentist(dentist);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Resource saved successfully");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }

    }

    @GetMapping("/getDentist/{dentistId}")
    public ResponseEntity<Dentist> getDentistByDentistId(@PathVariable Long dentistId)
    {

        try {
            Dentist dentist = dentistService.findByDentistId(dentistId);
            return ResponseEntity.ok(dentist);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateDentist/{dentistId}")
    public ResponseEntity<String> updateDentist(@RequestBody Dentist newDentist, @PathVariable Long dentistId)
    {
        try{
            dentistService.updateDentist(newDentist, dentistId);
            return ResponseEntity.ok("Updated Succesfully!");
        }catch (Exception e)
        {
            return ResponseEntity.status(400).body("Internal Error");
        }


    }

    @GetMapping("/getAllDentists")
    public List<Dentist> getAllDentists()
    {
        return dentistService.getAllDentists();
    }




    @DeleteMapping("/deleteDentistAndNullify/{dentistId}")
    public ResponseEntity<String> deleteDentistAndNullifyReferences(@PathVariable Long dentistId) throws ParseException, AppointmentOverlapException {
        System.out.println(dentistId);
        Dentist dentist = dentistService.findByDentistId(dentistId);

        System.out.println(dentist.getDentistId());

        // Nullify references in patients
        List<Patient> patients = patientService.getAllPatients(dentistId);
        patients.forEach(patient -> patient.setDentist(null));
        for(Patient patient: patients){
            patientService.savePatient(patient);
        }

        // Nullify references in appointments
        List<Appointment> appointments = appointmentService.getAllAppointments(dentistId);
        appointments.forEach(appointment -> appointment.setDentist(null));
        for(Appointment appointment: appointments){
            appointmentService.saveAppointment(appointment,null,appointment.getPatient().getSsn());
        }

        // Now, you can delete the dentist
        try {
            dentistService.deleteDentist(dentistId);
            return ResponseEntity.ok("Deleted Succesfully!");
        }catch (Exception e){
            return ResponseEntity.status(400).body("Internal Error");
        }

    }

}
