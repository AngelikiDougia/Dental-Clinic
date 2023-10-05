package com.administrationsystem.dentalClinic.serviceTesting;

import com.administrationsystem.dentalClinic.dao.LoginDao;
import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.models.dentist.DentistManager;
import com.administrationsystem.dentalClinic.repositories.DentistRepository;
import com.administrationsystem.dentalClinic.response.LoginResponse;
import com.administrationsystem.dentalClinic.services.DentistServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DentistServiceTest {

    @Mock
    private DentistRepository dentistRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private DentistManager dentistManager;

    @InjectMocks
    private DentistServiceImpl dentistService;


    @DisplayName("JUnit test for saveDentist in DentistService ")
    @Test
    public void DentistService_saveDentist_ReturnsDentistCorrectObject()
    {
        //Arrange
        Dentist dentist = new Dentist();
        dentist.setName("Georgios");
        dentist.setSurname("Georgiou");
        dentist.setEmail("georgiosgeorgiou@gmail.com");
        dentist.setUsername("username");
        dentist.setPassword("password");


        //when(dentistManager.createDentist(Mockito.any(Dentist.class))).thenReturn(dentist);
        when(passwordEncoder.encode("password")).thenReturn("$2a$12$ZWCqPm39RLsnQnKyNHwxS.15Ri3TDCLwoHQ9eKBl4U/YEiQDVsGTC");
        when(dentistRepository.save(Mockito.any(Dentist.class))).thenReturn(dentist);
        //Act
        Dentist actual_dentist = dentistService.saveDentist(dentist);


        //Assert
        assertNotNull(actual_dentist);
        assertEquals(actual_dentist.getName(), dentist.getName());

    }

    @DisplayName("JUnit test for getAllDentists in DentistService ")
    @Test
    public void DentistService_getAllDentists_returnListOfDentists()
    {
        //Arrange
        String [] names = {"Maria","Georgios"};
        String [] surnames = {"Ioannou","Georgiou"};
        List<Dentist> expectedDentists = new ArrayList<>();
        for(int i=0; i<2; i++){
            Dentist dentist = new Dentist();
            dentist.setName(names[i]);
            dentist.setSurname(surnames[i]);
            expectedDentists.add(dentist);
        }

        when(dentistRepository.findAll()).thenReturn(expectedDentists);

        //Act
        List<Dentist> actualDentists = dentistService.getAllDentists();

        //Assert
        assertEquals(expectedDentists.size(),actualDentists.size());
    }

    @DisplayName("JUnit test for findByUsername in DentistService")
    @Test
    public void DentistService_findByUsername_returnsDentist()
    {
        //Arrange
        Dentist dentist = new Dentist();
        dentist.setName("maria");
        dentist.setSurname("ioannou");
        dentist.setEmail("mariaioannou@outlook.com");
        dentist.setUsername("mariaIoa");
        when(dentistRepository.findByUsername(dentist.getUsername())).thenReturn(dentist);

        //Act
        Dentist actualDentist = dentistService.findByUsername(dentist.getUsername());

        //Assert
        assertEquals(dentist.getUsername(), actualDentist.getUsername());
    }

    @DisplayName("JUnit test for findByDentistId in DentistService")
    @Test
    public void DentistService_findByDentistId_returnsDentist()
    {
        //Arrange
        Dentist dentist = new Dentist();
        dentist.setName("maria");
        dentist.setSurname("ioannou");
        dentist.setEmail("mariaioannou@outlook.com");
        dentist.setUsername("mariaIoa");
        dentist.setDentistId(12L);
        when(dentistRepository.findByDentistId(12L)).thenReturn(dentist);

        //Act
        Dentist actualDentist = dentistService.findByDentistId(dentist.getDentistId());

        //Assert
        assertEquals(dentist.getDentistId(), actualDentist.getDentistId());
    }

    @DisplayName("JUnit test for loginDentist in DentistService")
    @Test
    public void DentistService_loginDentist_returnsCorrectLoginResponse()
    {
        //Arrange
        String username = "username";
        String password = "password";
        String encryptedPassword = "$2a$12$NrgLh.L2yFoQ3/FdAhxeoOoof/2zK0tdfJfCaYUSJ5sOFnhW9e2Fq"; //password is encrypted externally

        Dentist dentist = new Dentist();
        dentist.setPassword(encryptedPassword);
        dentist.setUsername(username);
        dentist.setDentistId(3L);
        LoginDao loginDao = new LoginDao(username,password);
        when(passwordEncoder.matches("password",encryptedPassword)).thenReturn(true);
        when(dentistRepository.findByUsername("username")).thenReturn(dentist);
        when(dentistRepository.findOneByUsernameAndPassword(username, encryptedPassword)).thenReturn(dentist);
        LoginResponse expectedLoginResponse = new LoginResponse("Login Success", true, 3L);
        //Act
        LoginResponse actualLoginResponse = dentistService.loginDentist(loginDao);

        //Assert
        assertNotNull(actualLoginResponse);
        assertEquals(actualLoginResponse.getMessage(), expectedLoginResponse.getMessage());
    }

    @DisplayName("JUnit test for deleteDentist in DentistService")
    @Test
    public void DentistService_deleteDentist_ReturnsNothing()
    {
        //Arrange
        Dentist dentist = new Dentist();
        dentist.setDentistId(3L);
        dentist.setName("Maria");
        dentist.setSurname("Ioannou");
        when(dentistRepository.findByDentistId(3L)).thenReturn(dentist);
        willDoNothing().given(dentistRepository).delete(dentist);

        //Act
        dentistService.deleteDentist(3L);
        //Assert
        verify(dentistRepository, times(1)).delete(dentist);
    }
}


