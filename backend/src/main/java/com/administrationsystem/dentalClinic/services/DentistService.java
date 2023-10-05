package com.administrationsystem.dentalClinic.services;

import com.administrationsystem.dentalClinic.dao.LoginDao;
import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.response.LoginResponse;

import java.util.List;

public interface DentistService
{



    public Dentist saveDentist(Dentist dentist);

    public List<Dentist> getAllDentists();

    public Dentist findByUsername(String username);

    public Dentist findByDentistId(Long dentistId);

    public LoginResponse loginDentist(LoginDao loginDao);


    public Dentist updateDentist(Dentist newDentist, Long dentistId);

    public void deleteDentist(Long id);
}
