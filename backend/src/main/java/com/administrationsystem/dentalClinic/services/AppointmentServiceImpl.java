package com.administrationsystem.dentalClinic.services;

import com.administrationsystem.dentalClinic.exceptions.AppointmentOverlapException;
import com.administrationsystem.dentalClinic.exceptions.ExistingPatientException;
import com.administrationsystem.dentalClinic.models.appointment.*;
import com.administrationsystem.dentalClinic.repositories.AppointmentRepository;
import com.administrationsystem.dentalClinic.repositories.DentistRepository;
import com.administrationsystem.dentalClinic.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService
{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DentistRepository dentistRepository;

    @Autowired
    private PatientRepository patientRepository;

    private AppointmentManager appointmentManager;
    @Override
    public List<Appointment> getAllAppointments(Long dentist_id) {

        List<Appointment> appointments = appointmentRepository.findByDentist_DentistId(dentist_id);
        return AppointmentManager.getInstance().getSortedByDateAndTime(appointments);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(Long dentist_id, String date) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date finalDate = formatter.parse(date);
        List<Appointment> appointments = appointmentRepository.findAppointmentsByDateAndDentistId(finalDate,dentist_id);

        Collections.sort(appointments, new TimeComparator());

        return appointments;
    }

    public String deleteAppointment(Long id){

        if(!appointmentRepository.existsById(Math.toIntExact(id))){
            return "something went wrong";
        }
        appointmentRepository.deleteById(Math.toIntExact(id));
        return "Appointment with id " + id + " has been deleted success.";
    }

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment updateAppointment(Appointment newAppointment, Long dentistId, String ssn) throws AppointmentOverlapException, ParseException {

        appointmentManager = AppointmentManager.getInstance();
        Appointment updatedAppointment = appointmentManager.updateAppointment(newAppointment);
        return saveAppointment(updatedAppointment, dentistId, ssn);
    }

    @Override
    public List<Appointment> getAppointmentsBySsn(String ssn) {
        return appointmentRepository.getAppointmentsByPatient_Ssn(ssn);
    }


    @Override
    public Appointment saveAppointment(Appointment appointment, Long dentistId, String ssn) throws AppointmentOverlapException, ParseException {

        try {
            // Check the validity of the appointment
            if (checkValidityOfAppointment(appointment, dentistId)) {
                // If it's valid, set the dentist and patient and save the appointment
                System.out.println("i get here 90");
                appointment.setDentist(dentistRepository.findByDentistId(dentistId));
                appointment.setPatient(patientRepository.findBySsn(ssn));

                return appointmentRepository.save(appointment);
            }
        } catch (AppointmentOverlapException e) {
            // If an overlap exception is caught, rethrow it
            throw e;
        }

        return null;
    }

    @Override
    public Appointment findByDateAndTime(Date date, String time) {
        return appointmentRepository.findByDateAndTime(date,time);
    }

    public Boolean checkValidityOfAppointment(Appointment appointment, Long dentistId) throws AppointmentOverlapException, ParseException {

        String dateString = convertDateToStringDate(appointment.getDate());

        if(checkIfDentistHasScheduledAppointmentForThisTimeAndDate(appointment,dentistId))
        {

            throw new AppointmentOverlapException("Exists scheduled Appointment for this date and time in database");

        } else if (getAppointmentsByDate(dentistId, dateString )!=null) {

            List<Appointment> appointments = getAppointmentsByDate(dentistId, dateString);
            checkForAppointmentOverlaps(appointments, appointment);

        }
        return true;
    }

    // Helper Functions
    public String convertDateToStringDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public  LocalTime convertStringTimeToLocalTime(String timeInString)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Parse the input time string into a LocalTime object
        return  LocalTime.parse(timeInString, formatter);
    }

    public boolean checkIfDentistHasScheduledAppointmentForThisTimeAndDate(Appointment inputAppointment,Long dentistId) throws ParseException {
        String date = convertDateToStringDate(inputAppointment.getDate());
        List<Appointment> scheduledAppointments = getAppointmentsByDate(dentistId, date);
        for(Appointment appointment: scheduledAppointments)
        {

            if(!appointment.getId().equals(inputAppointment.getId()))
            {


                if((appointment.getTime()).equals(inputAppointment.getTime()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkForAppointmentOverlaps(List<Appointment> appointments, Appointment appointment) throws AppointmentOverlapException {
        LocalTime appointmentTime = convertStringTimeToLocalTime(appointment.getTime());
        LocalTime appointmentTimePlusDuration = appointmentTime.plusMinutes(appointment.getDuration());

        for (Appointment scheduledAppointment : appointments) {
            if ((scheduledAppointment.getId()).equals(appointment.getId())) {
                continue; // Skip the current appointment
            }

            LocalTime scheduledAppointmentTime = convertStringTimeToLocalTime(scheduledAppointment.getTime());

            if (scheduledAppointmentTime.isBefore(appointmentTime)) {
                checkIfPreviousAppointmentOverlaps(scheduledAppointment, appointmentTime);
            } else if (scheduledAppointmentTime.isAfter(appointmentTime)) {
                checkIfNextAppointmentOverlaps(scheduledAppointment, appointmentTimePlusDuration);
            }
        }
    }

    private void checkIfPreviousAppointmentOverlaps(Appointment scheduledAppointment, LocalTime appointmentTime) throws AppointmentOverlapException {
        LocalTime scheduledAppointmentTime = convertStringTimeToLocalTime(scheduledAppointment.getTime());
        LocalTime scheduledAppointmentEndingTime = scheduledAppointmentTime.plusMinutes(scheduledAppointment.getDuration());

        if (scheduledAppointmentEndingTime.isAfter(appointmentTime)) {
            throw new AppointmentOverlapException("Start time of the appointment isn't valid because the previous appointment will not have finished yet.");
        }
    }


    private void checkIfNextAppointmentOverlaps(Appointment scheduledAppointment, LocalTime appointmentTimePlusDuration) throws AppointmentOverlapException {
        if (appointmentTimePlusDuration.isAfter(convertStringTimeToLocalTime(scheduledAppointment.getTime()))) {
            throw new AppointmentOverlapException("Duration of the appointment isn't valid because the next scheduled appointment is earlier");
        }
    }
}
