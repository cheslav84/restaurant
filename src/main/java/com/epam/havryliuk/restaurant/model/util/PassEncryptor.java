package com.epam.havryliuk.restaurant.model.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.security.GeneralSecurityException;
import java.util.Optional;

public class PassEncryptor {
    private static final Argon2 argon2;
    private static final int MEMORY = 15 * 1024;

    static {
        argon2 = Argon2Factory.create();
    }

    public static String encrypt(String password) {
        return Optional.of(argon2.hash(2, MEMORY, 1, password.toCharArray())).orElse("");
    }

    public static void verify(String hash, String password) throws GeneralSecurityException {
        if (!argon2.verify(hash, password.toCharArray())) {
            throw new GeneralSecurityException();
        }
    }

}
