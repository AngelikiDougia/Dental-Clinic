package com.administrationsystem.dentalClinic.controllerTesting;

import com.administrationsystem.dentalClinic.controllers.PatientController;
import com.administrationsystem.dentalClinic.models.patient.Patient;
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
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PatientControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("Junit test for addPatient in PatientController.")
    @Test
    public void  PatientController_addPatient_ReturnsCreated() throws Exception {

        Patient mockPatient = new Patient();
        mockPatient.setName("someone");
        mockPatient.setSurname("someone");
        mockPatient.setSsn("12345678");
        mockPatient.setEmail("someone@somewhere.com");

        String inputInJson = objectMapper.writeValueAsString(mockPatient);

        String url = "/patients/addPatient";

        Mockito.when(patientService.savePatient(Mockito.any(Patient.class),Mockito.anyLong())).thenReturn(mockPatient);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                        .post(url)
                                        .param("dentistId","3")
                                        .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        String successMessage = "Resource saved successfully";

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertThat(outputInJson).isEqualTo(successMessage);
    }

    @DisplayName("Junit test for getAllPatients in PatientController.")
    @Test
    public void PatientController_getAllPatients_ReturnsListObject() throws Exception {
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

        String URI = "/patients/getAllPatients/1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.objectMapper.writeValueAsString(mockPatients);
        String outputInJson = result.getResponse().getContentAsString();
        System.out.println(outputInJson);
        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @DisplayName("Junit test for getPatientBySsn in PatientController.")
    @Test
    public void PatientController_getPatientBySsn_ReturnsOkObject() throws Exception {
        Patient mockPatient = new Patient();
        mockPatient.setSsn("12345");
        mockPatient.setName("Martin Bingel");
        mockPatient.setSurname("Surname");
        mockPatient.setAddress("Delhi");
        mockPatient.setBirthdate(new Date());
        mockPatient.setEmail("martin.s2017@gmail.com");
        mockPatient.setGender("male");
        mockPatient.setTelephone("123456789");

        Mockito.when(patientService.findBySsn((Mockito.anyString()))).thenReturn(mockPatient);

        String URI = "/patients/getPatient/12345";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.objectMapper.writeValueAsString(mockPatient);
        String outputInJson = result.getResponse().getContentAsString();
        System.out.println(outputInJson);
        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @DisplayName("Junit test for deletePatient in PatientController.")
    @Test
    public void PatientController_deletePatient_ReturnsSuccessMessage() throws Exception {
        Patient mockPatient = new Patient();
        mockPatient.setSsn("12345");
        mockPatient.setName("Martin Bingel");
        mockPatient.setSurname("Surname");
        mockPatient.setAddress("Delhi");
        mockPatient.setBirthdate(new Date());
        mockPatient.setEmail("martin.s2017@gmail.com");
        mockPatient.setGender("male");
        mockPatient.setTelephone("123456789");

        Mockito.when(patientService.deletePatient((Mockito.anyString()))).thenReturn("");


        String url = "/patients/deletePatient/12345";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(url)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String successMessage = "Patient with ssn 12345 has been deleted success.";
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(successMessage);
    }

    @DisplayName("JUnit test for updatePatient in PatientController.")
    @Test
    public void PatientController_updatePatient_Returns() throws Exception {
        Patient mockPatient = new Patient();
        mockPatient.setSsn("12345");
        mockPatient.setName("Martin Bingel");
        mockPatient.setSurname("Surname");
        mockPatient.setAddress("Delhi");
        mockPatient.setBirthdate(new Date());
        mockPatient.setEmail("martin.s2017@gmail.com");
        mockPatient.setGender("male");
        mockPatient.setTelephone("123456789");

        Patient mockNewPatient = new Patient();
        mockPatient.setSsn("12345");
        mockPatient.setName("Martina ");
        mockPatient.setSurname("Surname");
        mockPatient.setAddress("Denmark");
        mockPatient.setBirthdate(new Date());
        mockPatient.setEmail("martin.s2017@gmail.com");
        mockPatient.setGender("female");
        mockPatient.setTelephone("123456789");

        Mockito.when(patientService.findBySsn((Mockito.anyString()))).thenReturn(mockPatient);
        Mockito.when(patientService.deletePatient(Mockito.anyString())).thenReturn("Patient with ssn " + mockPatient.getSsn() + " has been deleted.");
        Mockito.when(patientService.updatePatient(any(Patient.class),Mockito.anyLong())).thenReturn(mockNewPatient);

        String url = "/patients/updatePatient/12345";
        String inputInJson = objectMapper.writeValueAsString(mockPatient);
        String expectedOutputInJson = objectMapper.writeValueAsString(mockNewPatient);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url)
                .param("dentistId","3")
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedOutputInJson);
    }
}
