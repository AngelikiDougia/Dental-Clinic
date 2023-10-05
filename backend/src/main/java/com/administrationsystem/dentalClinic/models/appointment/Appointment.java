package com.administrationsystem.dentalClinic.models.appointment;
import java.util.Date;


import com.administrationsystem.dentalClinic.models.dentist.Dentist;
import com.administrationsystem.dentalClinic.models.patient.Patient;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "appointment")

public class Appointment {



    @Id
    @GeneratedValue
    private Long id;



    @Column(name = "appointment_date")
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd:MM:yyyy", timezone = "UTC")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date;


    @Column(name="appointment_time")
    @NotNull
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss", timezone = "UTC")
    //@Temporal(TemporalType.TIME)
    //@NotNull
    private String time;






    private String therapy;

    public Appointment()
    {}



    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    @Column(name = "appointment_duration")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "patient_ssn", referencedColumnName = "ssn")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dentist_id", referencedColumnName = "dentistId")
    private Dentist dentist;



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
