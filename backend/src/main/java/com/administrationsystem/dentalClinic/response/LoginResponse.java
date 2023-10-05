package com.administrationsystem.dentalClinic.response;

public class LoginResponse{


    private String message;
    private boolean status;

    private Long dentistId;

    public LoginResponse(String message, boolean status,Long dentist_id) {
        this.message = message;
        this.status = status;
        this.dentistId = dentist_id;
    }

    public Long getDentistId() {
        return dentistId;
    }

    public void setDentistId(Long dentistId) {
        this.dentistId = dentistId;
    }

    public LoginResponse() {
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
