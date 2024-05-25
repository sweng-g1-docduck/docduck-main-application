package com.docduck.application.data;

import com.docduck.application.xmldom.InvalidID;
import org.jdom2.Element;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * A Class for storing the non-secure data related to a user - The User's name -
 * The User's email - The User's role
 * 
 * @author jrb617
 */
public class User extends BaseData {

    private static final int USER_ID_PREFIX = 200;
    private final int id;
    private String name;
    private String username;
    private String passwordHash;
    private String email;
    private String role;

    public User(String name, String username, String passwordHash, String email, String role) {
        super();

        ArrayList<Integer> userIDs = domDataHandler.getListOfUserIDs();
        int counter = 1;
        
        while (userIDs.contains(addPrefix(counter,USER_ID_PREFIX))) {
            counter++;
        }

        this.id = addPrefix(counter,USER_ID_PREFIX);
        this.name = name;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
    }

    /**
     * Constructor to create a new User and populate the data from the xml files
     * 
     * @param id - The ID of the user to get out of the xml database
     * @throws InvalidID The provided id of the user doesn't exist in the data
     * @author William-A-B
     */
    public User(int id) throws InvalidID {
        super();
        if (!domDataHandler.checkIfIDExists(id)) {
            throw new InvalidID("ID does not exist in database, please provide an existing ID, or create a new user.");
        }

        List<Element> userData = domDataHandler.getUserData(id);

        this.id = id;

        for (Element target : userData) {

            if (target.getName().equals("name")) {
                this.name = target.getValue();
            }

            if (target.getName().equals("username")) {
                this.username = target.getValue();
            }

            if (target.getName().equals("password")) {
                this.passwordHash = target.getValue();
            }

            if (target.getName().equals("email")) {
                this.email = target.getValue();
            }

            if (target.getName().equals("role")) {
                this.role = target.getValue();
            }

        }
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
