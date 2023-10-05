package com.administrationsystem.dentalClinic.repositoryTesting;

import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.repositories.DentistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DentistRepositoryTest
{
    @Autowired
    private DentistRepository dentistRepository;


    @DisplayName("JUnit test for findByUsername method in DentistRepository")
    @Test
    public void DentistRepository_FindByUsername_ReturnDentist() {
        // Arrange
        String usernameToFind = "username";
        Dentist dentist = new Dentist();
        dentist.setUsername(usernameToFind);


        // Act
        dentistRepository.save(dentist);
        Dentist foundDentist = dentistRepository.findByUsername(usernameToFind);

                // Assert
        assertThat(foundDentist).isNotNull();
        assertEquals(usernameToFind, foundDentist.getUsername());

    }

    @DisplayName("JUnit test for findByDentistId method in DentistRepository")
    @Test
    public void DentistRepository_FindByDentistId_ReturnCorrectDentistId() {
        // Arrange
        Long dentistIdToFind;
        Dentist dentist = new Dentist();
        Dentist savedDentist = dentistRepository.save(dentist);
        dentistIdToFind= savedDentist.getDentistId();

        // Act
        Dentist foundDentist = dentistRepository.findByDentistId(dentistIdToFind);

        // Assert
        assertThat(foundDentist).isNotNull();
        assertEquals(dentistIdToFind, foundDentist.getDentistId());
    }

    @DisplayName("JUnit test for findOneByUsernameAndPassword")
    @Test
    public void DentistRepository_FindOneByUsernameAndPassword_ReturnCorrectDentistObject()
    {
        //Arrange
        Dentist dentist = new Dentist();
        dentist.setUsername("username");
        dentist.setPassword("password");


        //Act
        dentistRepository.save(dentist);
        Dentist foundDentist = dentistRepository.findOneByUsernameAndPassword(dentist.getUsername(), dentist.getPassword());


        //Assert
        assertThat(foundDentist).isNotNull();
        assertEquals(dentist.getUsername(), foundDentist.getUsername());
        assertEquals(dentist.getPassword(), foundDentist.getPassword());

    }

}
