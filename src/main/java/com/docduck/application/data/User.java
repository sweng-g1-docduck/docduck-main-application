package com.docduck.application.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * A Class for storing the non-secure data related to a user - The User's name -
 * The User's email - The User's role
 * 
 * @author jrb617
 */
public class User extends BaseData {


    private final int id;
    private String name;
    private String username;
    private String passwordHash;
    private String email;
    private String role;
    
    public User(int id, String name, String username, String passwordHash, String email, String role) {
        this.id = id; 
        this.name = name;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
    }
    
    public User (int id) {
        this.id = 0;
        
        // Populate from XML
        
//        List l = getUserData(id);
//        
//        name = jdomData.getDataValue(id, "name", DataType.USER);
//        for (Element target : l) {
//            if (target.getName().equals("name")) {
//                name = target.getValue();
//            }
//        }
    }

    private void updateXMLData() {
//        jdomData.setuserdata(this);
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    
    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public void setPassword(String password) {
        this.passwordHash = hashPassword(password);
    }

    private String hashPassword(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean checkPassword(String password) {
        return hashPassword(password).equals(passwordHash);
    }
}
