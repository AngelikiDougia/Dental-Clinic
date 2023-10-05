package com.administrationsystem.dentalClinic.models.patient;
import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "patient")
public class Patient {



    @Id
    private String  ssn; // Primary key


    private String email;
    private String name;
    private String surname;

    private String gender;

    private String telephone;

    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    //@NotNull
    private Date birthdate;

    @ManyToOne
    @JoinColumn(name = "dentist_id", referencedColumnName = "dentistId")
    private Dentist dentist; // Foreign key relationship



    private String address;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }



    // Constructors, getters, setters

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

}

