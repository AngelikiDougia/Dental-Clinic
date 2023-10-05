package com.administrationsystem.dentalClinic.models.appointment;

import java.util.*;

public class AppointmentManager
{

    private static AppointmentManager appointmentManager;

    private ArrayList<String> details;

    public AppointmentManager(){
        this.details = new ArrayList<String>();
        details.add("date");
        details.add("time");
        details.add("therapy");
        details.add("duration");
    }

    public Appointment updateAppointment(Appointment appointmentData)
    {
        Long id = appointmentData.getId();
        Appointment appointment = new Appointment();
        appointment.setId(id);

        for(String detail: details){
            setDetail(detail,appointment,appointmentData);
        }
        return appointment;

    }


    public void setDetail(String detail,Appointment appointment,Appointment AppointmentData)
    {

        switch (detail)
        {
            case "date" -> appointment.setDate(AppointmentData.getDate());
            case "time" -> appointment.setTime(AppointmentData.getTime());
            case "duration" -> appointment.setDuration(AppointmentData.getDuration());
            case "therapy" -> appointment.setTherapy(AppointmentData.getTherapy());
        }
    }

    public static AppointmentManager getInstance() {
        if (appointmentManager == null) {
            appointmentManager = new AppointmentManager();
        }
        return appointmentManager;
    }


    public List<Appointment> getSortedByDateAndTime(List<Appointment> appointments)
    {
        Set<Date> uniqueDates = new HashSet<>();

        // Iterate through the appointments and add their dates to the Set
        for (Appointment appointment : appointments) {
            uniqueDates.add(appointment.getDate());
        }

        Map<Date, List<Appointment>> appointmentsByDate = new TreeMap<>();

        // Initialize lists for each unique date
        for (Date date : uniqueDates) {
            appointmentsByDate.put(date, new ArrayList<>());
        }

        for (Appointment appointment : appointments) {
            Date date = appointment.getDate();
            List<Appointment> appointmentsForDate = appointmentsByDate.get(date);
            appointmentsForDate.add(appointment);
        }

        for (List<Appointment> appointmentsForDate : appointmentsByDate.values()) {
            Collections.sort(appointmentsForDate, new TimeComparator());
        }

        List<Appointment> combinedAppointments = new ArrayList<>();
        for (List<Appointment> appointmentsForDate : appointmentsByDate.values()) {
            combinedAppointments.addAll(appointmentsForDate);
        }
        return combinedAppointments;
    }
}
