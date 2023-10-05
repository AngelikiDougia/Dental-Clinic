package com.administrationsystem.dentalClinic.services;

import com.administrationsystem.dentalClinic.exceptions.AppointmentOverlapException;
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
                appointment.setDentist(dentistRepository.findByDentistId(dentistId));
                appointment.setPatient(patientRepository.findBySsn(ssn));
                return appointmentRepository.save(appointment);
            }
        } catch (AppointmentOverlapException e) {
            // If an overlap exception is caught, rethrow it
            throw e;
        }
        // You can handle other exceptions or return null or a default value here if needed
        return null;
    }

    @Override
    public Appointment findByDateAndTime(Date date, String time) {
        return appointmentRepository.findByDateAndTime(date,time);
    }

    public Boolean checkValidityOfAppointment(Appointment appointment, Long dentistId) throws AppointmentOverlapException, ParseException {

        String dateString = convertDateToStringDate(appointment.getDate());

        if(findByDateAndTime(appointment.getDate(),appointment.getTime())!=null && appointmentRepository.existsById(appointment.getId())==false)
        {
            throw new AppointmentOverlapException("Exists scheduled Appointment for this date and time in database");
        } else if (getAppointmentsByDate(dentistId, dateString )!=null) {

            LocalTime appointment_time = convertStringTimeToLocalTime(appointment.getTime());
            LocalTime appointment_timePlusDuration = appointment_time.plusMinutes(appointment.getDuration());

            for(Appointment scheduled_appointment : getAppointmentsByDate(dentistId,dateString)){
                if(scheduled_appointment.getId()== appointment.getId()) continue;
                LocalTime scheduled_appointment_time = convertStringTimeToLocalTime(scheduled_appointment.getTime());
                if(scheduled_appointment_time.isBefore(appointment_time))
                {
                    LocalTime scheduled_appointment_endingTime = scheduled_appointment_time.plusMinutes(scheduled_appointment.getDuration());
                    if(scheduled_appointment_endingTime.isAfter(appointment_time)) {
                        throw new AppointmentOverlapException("Start time of the appointment isn't valid because the previous appointment will not have finished yet.");
                    }

                } else if (scheduled_appointment_time.isAfter(appointment_time)) {

                    if(appointment_timePlusDuration.isAfter(scheduled_appointment_time))
                    {
                        throw new AppointmentOverlapException("Duration of the appointment isn't valid because the next scheduled appointment is earlier");
                    }
                }
            }
        }
        return true;
    }

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
}
