package com.administrationsystem.dentalClinic.services;

import com.administrationsystem.dentalClinic.exceptions.AppointmentOverlapException;
import com.administrationsystem.dentalClinic.models.appointment.Appointment;
import jakarta.transaction.Transactional;


import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Transactional
public interface AppointmentService{
    List<Appointment> getAllAppointments(Long dentist_id);

    List<Appointment> getAppointmentsByDate(Long dentist_id, String date) throws ParseException;

    Appointment saveAppointment(Appointment appointment, Long dentistId, String ssn) throws AppointmentOverlapException, ParseException;

    Appointment findByDateAndTime(Date date, String time);

    String deleteAppointment(Long id);

    Appointment findById(Long id);

    Appointment updateAppointment(Appointment newAppointment, Long dentistId, String ssn) throws AppointmentOverlapException, ParseException;

    List<Appointment> getAppointmentsBySsn(String ssn);


    //public List<Appointment> getAllAppointments(Long dentist_id);
}
