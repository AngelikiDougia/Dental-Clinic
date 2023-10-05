package com.administrationsystem.dentalClinic.controllers;


import com.administrationsystem.dentalClinic.exceptions.AppointmentOverlapException;
import com.administrationsystem.dentalClinic.models.appointment.Appointment;
import com.administrationsystem.dentalClinic.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/appointments")
public abstract class AppointmentController implements AppointmentService {



    @Autowired
    private AppointmentService appointmentService;


    @GetMapping("/getPatientAppointments/{ssn}")
    public List<Appointment> getPatientAppointments(@PathVariable ("ssn") String ssn)
    {
        return appointmentService.getAppointmentsBySsn(ssn);
    }

    @GetMapping("/getAllAppointments/{dentist_id}")
    public List<Appointment> getAllAppointments(@PathVariable("dentist_id") Long dentist_id)
    {

        System.out.println("I get the Appointments line 30");
        return appointmentService.getAllAppointments(dentist_id);
    }

    @GetMapping("/getAppointmentsByDate/{dentist_id}")
    public List<Appointment> getAllAppointments(@PathVariable("dentist_id") Long dentist_id,@RequestParam String date) throws ParseException {

        //System.out.println("I get the appointments by a specific date");
        return appointmentService.getAppointmentsByDate(dentist_id,date);
    }

    @DeleteMapping("/deleteAppointment/{id}")
    public String deletePatient(@PathVariable Long id)
    {
        //System.out.println("i have a delete Mapping !");

        appointmentService.deleteAppointment(id);
        return  "Appointment with id "+ id +" has been deleted success.";
    }

    @PostMapping("/addAppointment")
    public ResponseEntity<String> addAppointment(@RequestBody Appointment appointment,@RequestParam Long dentistId, @RequestParam String ssn){

        try {

            //System.out.println("ok 2!");
            appointmentService.saveAppointment(appointment, dentistId, ssn);
            // Return a successful response with 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Resource saved successfully");
        } catch (AppointmentOverlapException e) {
            // Handle the AppointmentOverlapException
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Return an error response with 500 Internal Server Error status
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }

    }

    @GetMapping("/getAppointment/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment!=null) {

            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateAppointment/{appointmentId}")
    public ResponseEntity<String> updateAppointment(@RequestBody Appointment newAppointment, @PathVariable Long appointmentId, @RequestParam Long dentistId, @RequestParam String ssn) throws AppointmentOverlapException, ParseException {
        if(appointmentService.findById(appointmentId)==null){
            return null;
        }else{
            try{
                appointmentService.updateAppointment(newAppointment,dentistId,ssn);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Resource saved successfully");
            } catch (AppointmentOverlapException e) {
                // Handle the AppointmentOverlapException
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } catch (Exception e) {
                // Return an error response with 500 Internal Server Error status
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred");
            }

        }

    }
}
