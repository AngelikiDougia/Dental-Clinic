package com.administrationsystem.dentalClinic.controllerTesting;

import com.administrationsystem.dentalClinic.controllers.AppointmentController;
import com.administrationsystem.dentalClinic.models.appointment.Appointment;
import com.administrationsystem.dentalClinic.services.AppointmentService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("Junit test for getAllAppointments in AppointmentController")
    @Test
    public void AppointmentController_getAllAppointments_ReturnsListAppointments() throws Exception {
        Appointment mockAppointment = new Appointment();
        mockAppointment.setDate(new Date());
        mockAppointment.setTime("11:15");
        mockAppointment.setTherapy("Tooth Filling");
        mockAppointment.setDuration(45);


        Appointment mockAppointment2 = new Appointment();
        mockAppointment2.setDate(new Date());
        mockAppointment2.setTime("12:15");
        mockAppointment2.setTherapy("Other");
        mockAppointment2.setDuration(30);

        List<Appointment> mockAppointments = new ArrayList<>();
        mockAppointments.add(mockAppointment);
        mockAppointments.add(mockAppointment2);
        Mockito.when(appointmentService.getAllAppointments((Mockito.anyLong()))).thenReturn(mockAppointments);

        String URI = "/appointments/getAllAppointments/1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.objectMapper.writeValueAsString(mockAppointments);
        String outputInJson = result.getResponse().getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @DisplayName("Junit test for getAppointmentsByDate in AppointmentController")
    @Test
    public void AppointmentController_getAppointmentsByDate_ReturnsListAppointments() throws Exception {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date finalDate = formatter.parse("2023-10-02");

        Appointment mockAppointment = new Appointment();
        mockAppointment.setDate(finalDate);
        mockAppointment.setTime("11:15");
        mockAppointment.setTherapy("Tooth Filling");
        mockAppointment.setDuration(45);


        Appointment mockAppointment2 = new Appointment();
        mockAppointment2.setDate(finalDate);
        mockAppointment2.setTime("12:15");
        mockAppointment2.setTherapy("Other");
        mockAppointment2.setDuration(30);
        List<Appointment> mockAppointments = new ArrayList<>();
        mockAppointments.add(mockAppointment);
        mockAppointments.add(mockAppointment2);
        Mockito.when(appointmentService.getAppointmentsByDate((Mockito.anyLong()),(Mockito.any()))).thenReturn(mockAppointments);

        String URI = "/appointments/getAppointmentsByDate/1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).param("date", String.valueOf(finalDate))
                .accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.objectMapper.writeValueAsString(mockAppointments);
        String outputInJson = result.getResponse().getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedJson);

    }

    @DisplayName("Junit test for deleteAppointment in AppointmentController.")
    @Test
    public void PatientController_deletePatient_ReturnsSuccessMessage() throws Exception {
        Appointment mockAppointment = new Appointment();
        mockAppointment.setDate(new Date());
        mockAppointment.setTime("11:15");
        mockAppointment.setTherapy("Tooth Filling");
        mockAppointment.setDuration(45);

        Mockito.when(appointmentService.deleteAppointment((Mockito.anyLong()))).thenReturn("");


        String url = "/appointments/deleteAppointment/1";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(url)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String successMessage = "Appointment with id 1 has been deleted success.";
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(successMessage);
    }

    @DisplayName("JUnit test for addAppointment in AppointmentController")
    @Test
    public void AppointmentController_addAppointment_ReturnsCreated() throws Exception {
        Appointment mockAppointment = new Appointment();
        mockAppointment.setDate(new Date());
        mockAppointment.setTime("11:15");
        mockAppointment.setTherapy("Tooth Filling");
        mockAppointment.setDuration(45);

        String inputInJson = objectMapper.writeValueAsString(mockAppointment);

        String url = "/appointments/addAppointment";

        Mockito.when(appointmentService.saveAppointment(Mockito.any(Appointment.class), Mockito.anyLong(), Mockito.anyString())).thenReturn(mockAppointment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .param("dentistId","3")
                .param("ssn", "123456789")
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        String successMessage = "Resource saved successfully";

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertThat(outputInJson).isEqualTo(successMessage);
    }

    @DisplayName("Junit test for getAppointmentById in AppointmenController.")
    @Test
    public void AppointmentController_getAppointmentById_ReturnsOkObject() throws Exception
    {
        Appointment mockAppointment = new Appointment();
        mockAppointment.setDate(new Date());
        mockAppointment.setTime("11:15");
        mockAppointment.setTherapy("Tooth Filling");
        mockAppointment.setDuration(45);

        Mockito.when(appointmentService.findById((Mockito.anyLong()))).thenReturn(mockAppointment);

        String URI = "/appointments/getAppointment/3";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.objectMapper.writeValueAsString(mockAppointment);
        String outputInJson = result.getResponse().getContentAsString();
        System.out.println(outputInJson);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @DisplayName("JUnit test for updateAppointment in AppointmentController")
    @Test
    public void AppointmentController_updateAppointment_ReturnsCorrectAppointmentObject() throws Exception {
        Appointment mockAppointment = new Appointment();
        mockAppointment.setDate(new Date());
        mockAppointment.setTime("11:15");
        mockAppointment.setTherapy("Tooth Filling");
        mockAppointment.setDuration(45);

        Appointment mockNewAppointment = new Appointment();
        mockNewAppointment.setDate(new Date());
        mockNewAppointment.setTime("11:15");
        mockNewAppointment.setTherapy("Tooth Cleaning");
        mockNewAppointment.setDuration(35);

        Mockito.when(appointmentService.findById((Mockito.anyLong()))).thenReturn(mockAppointment);
        Mockito.when(appointmentService.updateAppointment(any(Appointment.class),Mockito.anyLong(),Mockito.anyString())).thenReturn(mockNewAppointment);

        String url = "/appointments/updateAppointment/1";
        String inputInJson = objectMapper.writeValueAsString(mockNewAppointment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url)
                .param("dentistId","3")
                .param("ssn", "123456789")
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertThat(outputInJson).isEqualTo("Resource saved successfully");

    }
}
