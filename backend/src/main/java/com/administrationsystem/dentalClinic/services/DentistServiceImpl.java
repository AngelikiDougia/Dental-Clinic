package com.administrationsystem.dentalClinic.services;
import com.administrationsystem.dentalClinic.dao.LoginDao;
import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.models.dentist.DentistManager;
import com.administrationsystem.dentalClinic.repositories.DentistRepository;
import com.administrationsystem.dentalClinic.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentistServiceImpl implements DentistService

{
    @Autowired
    private DentistRepository dentistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private DentistManager dentistManager;

    @Override
    public Dentist saveDentist(Dentist dentist) {
        DentistManager dentistManager = DentistManager.getInstance();
        Dentist newDentist = dentistManager.createDentist(dentist);
        newDentist.setPassword(passwordEncoder.encode(dentist.getPassword()));

        return dentistRepository.save(newDentist);

    }


    @Override
    public List<Dentist> getAllDentists() {
        return dentistRepository.findAll();
    }

    @Override
    public Dentist findByUsername(String username) {
        return dentistRepository.findByUsername(username);
    }

    @Override
    public Dentist findByDentistId(Long dentistId) {
        return dentistRepository.findByDentistId(dentistId);
    }

    @Override
    public LoginResponse loginDentist(LoginDao loginDao) {
        String msg = "";
        Dentist dentist = dentistRepository.findByUsername(loginDao.getUsername());
        if (dentist != null) {
            String password = loginDao.getPassword();
            String encodedPassword = dentist.getPassword();

            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Dentist dentistProfile = dentistRepository.findOneByUsernameAndPassword(loginDao.getUsername(), encodedPassword);
                if (dentistProfile!=null) {
                    return new LoginResponse("Login Success", true, dentistProfile.getDentistId());
                } else {
                    return new LoginResponse("Login Failed", false, (long) -1);
                }
            } else {

                return new LoginResponse("password Not Match", false, (long) -1);
            }
        }else {
            return new LoginResponse("username not exists", false, (long) -1);
        }
    }

    @Override
    public Dentist updateDentist(Dentist dentist, Long dentistId) {

        Dentist oldDentist = dentistRepository.findByDentistId(dentistId);
        DentistManager dentistManager = DentistManager.getInstance();
        oldDentist = dentistManager.updateDentist(oldDentist,dentist);
        return dentistRepository.save(oldDentist);
    }

    @Override
    public void deleteDentist(Long id) {
        dentistRepository.delete(dentistRepository.findByDentistId(id));
    }


}
