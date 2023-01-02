package com.epam.havryliuk.restaurant.model.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

public class PassEncryptor {
    private static final Argon2 argon2;
    private static final int MEMORY = 15 * 1024;


    static {
        argon2 = Argon2Factory.create();
    }

    public static String encrypt(String password) throws GeneralSecurityException {
        return Optional.of(argon2.hash(2, MEMORY, 1, password.toCharArray())).orElse("");
    }


    public static void verify(String hash, String password) throws GeneralSecurityException {
        if (!argon2.verify(hash, password.toCharArray())){
            throw new GeneralSecurityException();
        }
    }

}
