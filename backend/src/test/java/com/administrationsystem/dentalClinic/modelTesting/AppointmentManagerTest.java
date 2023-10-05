package com.administrationsystem.dentalClinic.modelTesting;

import com.administrationsystem.dentalClinic.models.appointment.Appointment;
import com.administrationsystem.dentalClinic.models.appointment.AppointmentManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;

public class AppointmentManagerTest
{

    private AppointmentManager appointmentManager = AppointmentManager.getInstance();



    @DisplayName("JUnit test for updateAppointment in AppointmentManager")
    @Test
    public void AppointmentManager_UpdateAppointment_ReturnsCorrectAppointmentObject() {
        // Create a sample appointment data

        //Arrange
        Appointment appointmentData = new Appointment();
        appointmentData.setId(1L);
        appointmentData.setDate(new Date());
        appointmentData.setTime("10:00 AM");
        appointmentData.setDuration(60);
        appointmentData.setTherapy("Tooth Cleaning");

        //Act
        Appointment updatedAppointment = appointmentManager.updateAppointment(appointmentData);

        // Assert
        assertNotNull(updatedAppointment);
        assertEquals(appointmentData.getId(), updatedAppointment.getId());
        assertEquals(appointmentData.getDate(), updatedAppointment.getDate());
        assertEquals(appointmentData.getTime(), updatedAppointment.getTime());
        assertEquals(appointmentData.getDuration(), updatedAppointment.getDuration());
        assertEquals(appointmentData.getTherapy(), updatedAppointment.getTherapy());
    }

    @DisplayName("JUnit test for getSortedByDateAndTime in AppointmentManager")
    @Test
    public void AppointmentManager_GetSortedByDateAndTime_ReturnsSortedAppointmentsList() throws ParseException {
        //Arrange
        List<Appointment> appointments = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = simpleDateFormat.parse("2023-10-02");
        Date date1 = simpleDateFormat.parse("2023-10-01");

        ArrayList<String> times = new ArrayList<String>(Arrays.asList("09:00","14:30","11:30","10:00"));
        for(int i=0; i<4; i++)
        {
            Appointment appointment = new Appointment();
            if(i%2==0){
                appointment.setDate(date2);
            }else{
                appointment.setDate(date1);
            }

            appointment.setTime(times.get(i));
            appointments.add(appointment);
        }




        //Act
        List<Appointment> sortedAppointments = appointmentManager.getSortedByDateAndTime(appointments);

        // Assert
        assertEquals(date1, sortedAppointments.get(0).getDate());
        assertEquals(date1, sortedAppointments.get(1).getDate());
        assertEquals(date2, sortedAppointments.get(2).getDate());
        assertEquals(date2, sortedAppointments.get(3).getDate());
        assertEquals("10:00", sortedAppointments.get(0).getTime());
        assertEquals("14:30", sortedAppointments.get(1).getTime());
        assertEquals("09:00", sortedAppointments.get(2).getTime());
        assertEquals("11:30", sortedAppointments.get(3).getTime());
    }


}
