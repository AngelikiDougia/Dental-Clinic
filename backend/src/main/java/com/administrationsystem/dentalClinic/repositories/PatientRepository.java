package com.administrationsystem.dentalClinic.repositories;
import com.administrationsystem.dentalClinic.models.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer>
{




    Patient findBySsn(String ssn);

    boolean existsBySsn(String ssn);

    void deleteBySsn(String ssn);

    List<Patient> findByDentist_DentistId(Long dentistId);
}
