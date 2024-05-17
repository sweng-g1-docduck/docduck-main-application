package com.docduck.application.data;

public class User {

    private String name;
    private String email;
    private String role;


    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        // First part of the email before the '@' symbol
        int atIndex = email.indexOf('@');
        if (atIndex != -1) {
            return email.substring(0, atIndex);
        } else {
            // If email format is invalid, return the entire email
            return email;
        }
    }
}
