package com.docduck.application.data;

import com.docduck.application.xmldom.InvalidID;
import org.jdom2.Element;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

/**
 * A Class for storing the non-secure data related to a user - The User's name -
 * The User's email - The User's role
 * 
 * @author jrb617
 */
public class User extends BaseData {

    private String name;
    private String email;
    private String role;
    private String passwordHash;

    public User(String name, String email, String role, String password) {
        super();
        this.name = name;
        this.email = email;
        this.role = role;
        setPassword(password);
    }

    /**
     * Constructor to create a new User and populate the data from the xml files
     *
     * @param id - The ID of the user to get out of the xml database
     * @throws InvalidID The provided id of the user doesn't exist in the data
     * @author William-A-B
     */
    public User(int id) throws InvalidID {

        if (!domDataHandler.checkIfIDExists(id)) {
            throw new InvalidID(
                    "ID does not exist in database, please provide an existing ID, or create a new User.");
        }

        List<Element> userData = domDataHandler.getReportData(id);

        for (Element target : userData) {

            if (target.getName().equals("name")) {
                this.name = target.getValue();
            }

            // TODO Complete data population
        }
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
        }
        else {
            // If email format is invalid, return the entire email
            return email;
        }
    }

    private void setPassword(String password) {
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
