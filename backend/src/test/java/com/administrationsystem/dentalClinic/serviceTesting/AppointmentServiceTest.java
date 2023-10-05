package com.administrationsystem.dentalClinic.serviceTesting;

import com.administrationsystem.dentalClinic.exceptions.AppointmentOverlapException;
import com.administrationsystem.dentalClinic.models.appointment.Appointment;
import com.administrationsystem.dentalClinic.models.appointment.AppointmentManager;
import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.models.patient.Patient;
import com.administrationsystem.dentalClinic.repositories.AppointmentRepository;
import com.administrationsystem.dentalClinic.repositories.DentistRepository;
import com.administrationsystem.dentalClinic.repositories.PatientRepository;
import com.administrationsystem.dentalClinic.services.AppointmentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest
{

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DentistRepository dentistRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentManager appointmentManager;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;






    @DisplayName("JUnit test for deleteAppointment in AppointmentService")
    @Test
    public void AppointmentService_deleteAppointment_ReturnsNothing(){
        // Arrange
        Long id = 3L;

        when(appointmentRepository.existsById(Math.toIntExact(id))).thenReturn(true);
        willDoNothing().given(appointmentRepository).deleteById(Math.toIntExact(id));

        //Act
        String response = appointmentService.deleteAppointment(id);

        // Assert
        verify(appointmentRepository, times(1)).deleteById(Math.toIntExact(id));
        assertEquals("Appointment with id " + id + " has been deleted success.", response);
    }

    @DisplayName("JUnit test for findById in AppointmentService")
    @Test
    public void AppointmentService_findById_ReturnsAppointment()
    {
        //Arrange
        Appointment appointment = new Appointment();
        appointment.setTime("10:30");
        appointment.setDuration(25);

        when(appointmentRepository.findById(3L)).thenReturn(appointment);

        //Act
        Appointment actual_Appointment = appointmentService.findById(3L);

        //Assert
        assertNotNull(actual_Appointment);
        assertEquals(appointment.getTime(),actual_Appointment.getTime());
        assertEquals(appointment.getDuration(), actual_Appointment.getDuration());
    }

    @DisplayName("JUnit test for saveAppointment in AppointmentService")
    @Test
    public void AppointmentService_saveAppointment_ReturnsAppointment() throws AppointmentOverlapException, ParseException {
        //Arrange
        Appointment appointment = new Appointment();
        appointment.setDate(new Date());
        appointment.setTime("10:30");
        appointment.setDuration(25);

        Dentist dentist = new Dentist();
        dentist.setName("maria");
        dentist.setSurname("ioannou");

        Patient patient = new Patient();
        patient.setName("giorgos");
        patient.setSurname("georgiou");
        patient.setSsn("123456789");

        when(dentistRepository.findByDentistId(3L)).thenReturn(dentist);
        when(patientRepository.findBySsn("123456789")).thenReturn(patient);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        //Act
        Appointment actual_Appointment = appointmentService.saveAppointment(appointment,3L,"123456789");

        //Assert
        assertNotNull(actual_Appointment);
        assertEquals(appointment.getTime(),actual_Appointment.getTime());
        assertEquals(appointment.getDuration(), actual_Appointment.getDuration());
    }

    @DisplayName("JUnit test for findByDateAndTime in AppointmentService")
    @Test
    public void AppointmentService_findByDateAndTime_ReturnsAppointment() throws ParseException {
        //Arrange
        Appointment appointment = new Appointment();
        appointment.setTime("10:30");
        appointment.setDuration(25);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = "2023-09-30";
        Date finalDate = formatter.parse(dateInString);

        String time = "11:30";

        when(appointmentRepository.findByDateAndTime(finalDate, time)).thenReturn(appointment);

        //Act
        Appointment actualAppointment = appointmentService.findByDateAndTime(finalDate, time);


        //Assert
        assertNotNull(actualAppointment);
        assertEquals(appointment.getTime(),actualAppointment.getTime());
        assertEquals(appointment.getDuration(),actualAppointment.getDuration());
    }

    @DisplayName("JUnit test for getAppointmentsBySsn in AppointmentService")
    @Test
    public void AppointmentService_getAppointmentsBySsn_ReturnsCorrectListOfAppointments()
    {
        //Arrange
        Patient patient = new Patient();
        String ssn = "987654321";
        patient.setSsn(ssn);
        List<Appointment> expectedAppointments = new ArrayList<>();

        for(int i=0; i<4; i++)
        {
            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            expectedAppointments.add(appointment);
        }
        Mockito.when(appointmentRepository.getAppointmentsByPatient_Ssn(ssn)).thenReturn(expectedAppointments);
        //Act
        List<Appointment> actualAppointments = appointmentService.getAppointmentsBySsn(ssn);

        //Assert
        assertNotNull(actualAppointments);
        assertEquals(expectedAppointments.size(), actualAppointments.size());

    }
}
