package com.docduck.application.HashTestHarness;

import com.docduck.application.passwordHash.Bcrypt;

public class Hashtest {
    public static void main(String[] args) throws Exception {
        System.out.println("Running Bcrypt password hashing and verification tests...");

        // Test password
        String testPassword = "Docduck123";
        
        // Hash the test password
        String hashedPassword = Bcrypt.hashPassword(testPassword);
        System.out.println("\nHashed password: " + hashedPassword);

        // Verify the password
        String inputPassword = "Docduck123"; // Password to verify
        boolean isPasswordCorrect = Bcrypt.verifyPassword(inputPassword, hashedPassword);
        
        // Print verification result
        if (isPasswordCorrect) {
            System.out.println("Password verification successful!");
        } else {
            System.out.println("Password verification failed!");
        }
    }
}
