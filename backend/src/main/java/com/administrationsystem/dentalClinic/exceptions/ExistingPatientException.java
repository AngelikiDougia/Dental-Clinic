package com.administrationsystem.dentalClinic.exceptions;

public class ExistingPatientException extends Exception{


    public ExistingPatientException()
    {

        super("Ssn exists in database for a registered patient.");
    }
}
