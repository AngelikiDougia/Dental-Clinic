package com.administrationsystem.dentalClinic.models.appointment;

import java.util.Comparator;

public class DateComparator implements Comparator<Appointment>
{


    @Override
    public int compare(Appointment o1, Appointment o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
