package com.administrationsystem.dentalClinic.controllerTesting;
import com.administrationsystem.dentalClinic.controllers.AdministratorController;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = AdministratorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AdministratorControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Junit test for login in AdministratorController")
    @Test
    public void AdministratorController_login_ReturnsOKStatus() throws Exception {
        String url = "/administrator/login";

        Mockito.when(passwordEncoder.matches("admin","$2a$12$bLl5ZUKQGvRvMPnh278mE.d7F/gfPt2yrU.agSkjFefOkWItsgYxa")).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .param("username","admin")
                .param("password", "admin")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        String successMessage = "Successful Login";

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertThat(outputInJson).isEqualTo(successMessage);
    }
}
