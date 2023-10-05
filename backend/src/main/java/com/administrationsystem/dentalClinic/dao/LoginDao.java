package com.administrationsystem.dentalClinic.dao;

public class LoginDao {

    private String username;
    private String password;

    public LoginDao(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDao{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
