package com.administrationsystem.dentalClinic.repositories;

import com.administrationsystem.dentalClinic.models.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("SELECT a FROM Appointment a WHERE a.date = :date AND a.dentist.dentistId = :dentistId")
    List<Appointment> findAppointmentsByDateAndDentistId(@Param("date") Date date, @Param("dentistId") Long dentistId);


    List<Appointment> getAppointmentsByPatient_Ssn(String ssn);

    Appointment findByDateAndTime(Date date, String time);

    Appointment findById(Long id);

    List<Appointment> findByDentist_DentistId(Long dentist_id);

    boolean existsById(Long id);



}

