package com.administrationsystem.dentalClinic.models.appointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class TimeComparator implements Comparator<Appointment> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @Override
    public int compare(Appointment o1, Appointment o2)
    {
        try {
            Date time1 = dateFormat.parse(o1.getTime());
            Date time2 = dateFormat.parse(o2.getTime());

            return time1.compareTo(time2);
        } catch (ParseException e) {
            // Handle parsing exceptions if necessary
            e.printStackTrace();
        }

        // Return 0 for equality or handle errors in another way
        return 0;
    }
}
