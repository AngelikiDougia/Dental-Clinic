package com.administrationsystem.dentalClinic.repositories;

import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistRepository extends JpaRepository<Dentist, Integer>
{

    public Dentist findByUsername(String username);

    public Dentist findByDentistId(Long dentistId);


    public Dentist findOneByUsernameAndPassword(String username, String encodedPassword);



    public  void delete(Dentist dentist);

}
