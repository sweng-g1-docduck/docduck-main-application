package com.docduck.application.data;

/**
 * A Class for storing the non-secure data related to a user
 * - The User's name
 * - The User's email
 * - The User's role
 * 
 * @author jrb617
 */
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
    
}
