package com.codecool.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Password {
    private static int toughness = 12;

    public static String hasher(String password) {
        String salt = BCrypt.gensalt(toughness);
        String hashed_password = BCrypt.hashpw(password, salt);
        return(hashed_password);
    }

    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }
}
