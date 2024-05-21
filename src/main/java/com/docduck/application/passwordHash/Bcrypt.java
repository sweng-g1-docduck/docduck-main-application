package com.docduck.application.passwordHash;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Bcrypt {
	
	public static String hashPassword(String password) {
        BCryptPasswordEncoder DocduckPasswordEncoder = new BCryptPasswordEncoder();
        return DocduckPasswordEncoder.encode(password);
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) {
        BCryptPasswordEncoder DocduckEncoder = new BCryptPasswordEncoder();
        return DocduckEncoder.matches(inputPassword, storedHash);
    }
}

