package com.ahmedosman.tripplanner;

/**
 * Created by Ahmed on 07-Feb-18.
 */

public class User {
    private String email;
    private String password;

    public User(String _email, String _password){
        email = _email;
        password = _password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
