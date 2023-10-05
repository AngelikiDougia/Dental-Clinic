package com.administrationsystem.dentalClinic.repositoryTesting;

import com.administrationsystem.dentalClinic.models.appointment.Appointment;
import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.repositories.AppointmentRepository;
import com.administrationsystem.dentalClinic.repositories.DentistRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AppointmentRepositoryTest
{
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DentistRepository dentistRepository;

    @DisplayName("JUnit Test for findAppointmentsByDateAndDentistId in AppointmentRepository")
    @Test
    public void AppointmentRepository_findAppointmentsByDateAndDentistId_ReturnCorrectListOfAppointments() throws ParseException {
        //Arrange
        Dentist dentist = new Dentist();
        dentist.setName("someone");
        dentist.setSurname("somefamilyname");
        dentist = dentistRepository.save(dentist);
        //System.out.println("dentistId:" + dentist.getDentistId());

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = "2023-09-30";
        Date finalDate = formatter.parse(dateInString);

        Appointment appointment = new Appointment();
        appointment.setTime("10:20");
        appointment.setDate(finalDate);
        Appointment appointment2 = new Appointment();
        appointment.setTime("11:20");

        appointment.setDentist(dentist);
        appointment2.setDentist(dentist);
        appointment2.setDate(finalDate);
        appointmentRepository.save(appointment);
        appointmentRepository.save(appointment2);

        //Act
        Long dentistId = dentist.getDentistId();
        List<Appointment> appointments = appointmentRepository.findAppointmentsByDateAndDentistId(finalDate,dentistId);

        //Assert
        assertNotNull(appointments);
        assertEquals(2,appointments.size());

    }










    @DisplayName("JUnit Test for findByDateAndTime method in AppointmentRepository")
    @Test
    public void AppointmentRepository_FindByDateAndTime_ReturnCorrectAppointment() throws ParseException {
        // Arrange
        //Date date = new Date(); // Replace with your desired date
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = "2023-09-30";
        Date finalDate = formatter.parse(dateInString);

        String time = "10:00 AM";

        Appointment expectedAppointment = new Appointment();
        expectedAppointment.setDate(finalDate);
        expectedAppointment.setTime(time);

        appointmentRepository.save(expectedAppointment);

        // Act
        Appointment foundAppointment = appointmentRepository.findByDateAndTime(finalDate, time);

        // Assert
        assertNotNull(foundAppointment);
        assertEquals(finalDate, foundAppointment.getDate());
        assertEquals(time, foundAppointment.getTime());
    }

    @DisplayName("JUnit Test for findById method in AppointmentRepository")
    @Test
    public void AppointmentRepository_FindById_ReturnCorrectAppointment() throws ParseException {
        // Arrange

        Appointment expectedAppointment = new Appointment();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = "2023-09-30";
        Date finalDate = formatter.parse(dateInString);

        String time = "10:30 AM";
        expectedAppointment.setDate(finalDate);
        expectedAppointment.setTime(time);

        appointmentRepository.save(expectedAppointment);
        Long id = appointmentRepository.findByDateAndTime(finalDate,time).getId();
        // Act
        Appointment foundAppointment = appointmentRepository.findById(id);

        // Assert
        assertNotNull(foundAppointment);
        assertEquals(id, foundAppointment.getId());
        assertEquals(time,foundAppointment.getTime());
    }

    @DisplayName("JUnit Test for FindByDentist_DentistId method in AppointmentRepository")
    @Test
    public void AppointmentRepository_FindByDentistDentistId_ReturnCorrectAppointmentList() throws ParseException {
        // Arrange
        Dentist dentist = dentistRepository.save(new Dentist());
        Long dentist_id = dentist.getDentistId();

        for(int i=0; i<3; i++){
            Appointment appointment = new Appointment();
            appointment.setDentist(dentist);
            appointmentRepository.save(appointment);
        }
        //Act
        List <Appointment> actual_appointments = appointmentRepository.findByDentist_DentistId(dentist_id);

        //Assert
        assertNotNull(actual_appointments);
        assertEquals(3, actual_appointments.size());
    }


}
