package com.administrationsystem.dentalClinic.controllerTesting;

import com.administrationsystem.dentalClinic.controllers.DentistController;
import com.administrationsystem.dentalClinic.dao.LoginDao;
import com.administrationsystem.dentalClinic.models.appointment.Appointment;
import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.models.patient.Patient;
import com.administrationsystem.dentalClinic.response.LoginResponse;
import com.administrationsystem.dentalClinic.services.AppointmentService;
import com.administrationsystem.dentalClinic.services.DentistService;
import com.administrationsystem.dentalClinic.services.PatientService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = DentistController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DentistControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DentistService dentistService;

    @MockBean
    private PatientService patientService;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Junit test for loginDentist in DentistController")
    @Test
    public void DentistController_loginDentist_ReturnsOKStatus() throws Exception {
        LoginDao mockLogin = new LoginDao("mariaioa","mariaioa");
        LoginResponse mockResponse = new LoginResponse("Login Success",true, 3L);
        String inputInJson = objectMapper.writeValueAsString(mockLogin);

        String url = "/login";

        Mockito.when(dentistService.loginDentist(Mockito.any(LoginDao.class))).thenReturn(mockResponse);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @DisplayName("Junit test for addDentist in DentistController")
    @Test
    public void DentistController_addDentist_ReturnsCreatedStatus() throws Exception {

        Dentist mockDentist = new Dentist();
        mockDentist.setEmail("maria@gmail.com");
        mockDentist.setName("maria");
        mockDentist.setSurname("ioannou");
        mockDentist.setUsername("mariaioa");


        Dentist mockSavedDentist = new Dentist();

        String inputInJson = objectMapper.writeValueAsString(mockDentist);

        String url = "/createDentist";

        Mockito.when(dentistService.saveDentist(Mockito.any(Dentist.class))).thenReturn(mockSavedDentist);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        String successMessage = "Resource saved successfully";

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertThat(outputInJson).isEqualTo(successMessage);
    }

    @DisplayName("JUnit test for getDentistByDentistId")
    @Test
    public void DentistController_getDentistByDentistId_ReturnsOkStatus() throws Exception {
        Dentist mockDentist = new Dentist();
        mockDentist.setEmail("maria@gmail.com");
        mockDentist.setName("maria");
        mockDentist.setSurname("ioannou");
        mockDentist.setUsername("mariaioa");


        Dentist mockFoundDentist = new Dentist();
        String url = "/getDentist/3";
        Mockito.when(dentistService.findByDentistId(3L)).thenReturn(mockDentist);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                url).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.objectMapper.writeValueAsString(mockDentist);
        String outputInJson = result.getResponse().getContentAsString();


        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @DisplayName("JUnit test for updateDentist in DentistController")
    @Test
    public void DentistController_updateDentist_ReturnsOkStatus() throws Exception {
        Long dentistId = 3L;
        Dentist mockDentist = new Dentist();
        mockDentist.setEmail("maria@gmail.com");
        mockDentist.setName("maria");
        mockDentist.setSurname("ioannou");
        mockDentist.setUsername("mariaioa");

        Dentist mockNewDentist = new Dentist();
        mockNewDentist.setEmail("mariaIoannou@gmail.com");
        mockNewDentist.setName("maria");
        mockNewDentist.setSurname("ioannou");
        mockNewDentist.setUsername("maria_ioa");

        Mockito.when(dentistService.updateDentist(any(Dentist.class),Mockito.anyLong())).thenReturn(mockNewDentist);

        String url = "/updateDentist/" + dentistId.toString();
        String inputInJson = objectMapper.writeValueAsString(mockDentist);
        String expectedOutputInJson = "Updated Succesfully!";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
        assertThat(outputInJson).isEqualTo(expectedOutputInJson);
    }

    @DisplayName("JUnit test for getAllDentists in DentistController")
    @Test
    public void DentistController_getAllDentists_ReturnsCorrectList() throws Exception {
        Dentist mockDentist = new Dentist();
        mockDentist.setDentistId(1L);
        mockDentist.setName("Martin Bingel");
        mockDentist.setSurname("Surname");
        mockDentist.setAddress("London");
        mockDentist.setEmail("martin.s2017@gmail.com");
        mockDentist.setTelephone("123456789");

        Dentist mockDentist2 = new Dentist();
        mockDentist2.setDentistId(2L);
        mockDentist2.setName("Martina Bingel");
        mockDentist2.setSurname("Surname");
        mockDentist2.setAddress("Paris");
        mockDentist2.setEmail("martina.s2019@gmail.com");
        mockDentist2.setTelephone("123456779");
        List<Dentist> mockDentists = new ArrayList<>();
        mockDentists.add(mockDentist);
        mockDentists.add(mockDentist2);

        Mockito.when(dentistService.getAllDentists()).thenReturn(mockDentists);

        String URL = "/getAllDentists";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URL).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.objectMapper.writeValueAsString(mockDentists);
        String outputInJson = result.getResponse().getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @DisplayName("JUnit test for deleteDentistAndNullifyReferences in DentistController.")
    @Test
    public void DentistController_deleteDentistAndNullifyReferences_ReturnsCorrect() throws Exception {
        Patient mockPatient = new Patient();
        mockPatient.setSsn("12345");
        mockPatient.setName("Martin Bingel");
        mockPatient.setSurname("Surname");
        mockPatient.setAddress("Delhi");
        mockPatient.setBirthdate(new Date());
        mockPatient.setEmail("martin.s2017@gmail.com");
        mockPatient.setGender("male");
        mockPatient.setTelephone("123456789");

        Patient mockPatient2 = new Patient();
        mockPatient2.setSsn("54321");
        mockPatient2.setName("Martina Bingel");
        mockPatient2.setSurname("Surname");
        mockPatient2.setAddress("Delhi");
        mockPatient2.setBirthdate(new Date());
        mockPatient2.setEmail("martina.s2019@gmail.com");
        mockPatient2.setGender("female");
        mockPatient2.setTelephone("123456789");
        List<Patient> mockPatients = new ArrayList<>();
        mockPatients.add(mockPatient);
        mockPatients.add(mockPatient2);
        Mockito.when(patientService.getAllPatients((Mockito.anyLong()))).thenReturn(mockPatients);
        Mockito.when(patientService.savePatient(any(Patient.class))).thenReturn(any(Patient.class));

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
        Mockito.when(appointmentService.getAllAppointments(1L)).thenReturn(mockAppointments);
        Mockito.when(appointmentService.saveAppointment(any(Appointment.class), Mockito.anyLong(), Mockito.anyString())).thenReturn(mockAppointment);

        Dentist dentist = new Dentist();
        dentist.setDentistId(1L);
        Mockito.when(dentistService.findByDentistId(Mockito.anyLong())).thenReturn(dentist);
        Mockito.doNothing().when(dentistService).deleteDentist(1L);

        String url = "/deleteDentistAndNullify/1";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(url)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String message = "Deleted Succesfully!";
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(message);
    }
}
