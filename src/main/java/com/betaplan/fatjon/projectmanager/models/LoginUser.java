package com.betaplan.fatjon.projectmanager.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginUser {
    @NotBlank
    @Email
    @Size(min=5)
    private String email;
    @NotBlank
    @Size(min=8)
    private String password;
    public LoginUser(){}

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
