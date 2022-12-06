package com.havryliuk.restaurant.utils;

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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PassEncryptor {
    private static final Logger log = LogManager.getLogger(PassEncryptor.class);// todo add logs for class
    public static String encrypt(String password) throws GeneralSecurityException {
        final byte[] salt = ("622836429").getBytes();
        final int iterationCount = 10000;
        final int keyLength = 128;
        String encryptedPassword;

        try {
            SecretKeySpec key = secretKey.generateSecretKey(password.toCharArray(), salt, iterationCount, keyLength);
            encryptedPassword = secretKey.encryptPassword(password, key);
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            log.error("Error occurs encrypting user password. " + e);
            throw new GeneralSecurityException(e);
        }

        System.out.println("Original password: " + password);
        System.out.println("Encrypted password: " + encryptedPassword);
        System.out.println("Encrypted password: " + "90CgNvHrdxwQZVd4XUMELw==:0aba5Q6nTt6DxT84xxi5bA==");
        System.out.println(encryptedPassword.equals("wGNL1DTtcS9pF11rpkk7Fg==:6SmOO/e5lB7QyaPnR/UyJA=="));
        System.out.println(encryptedPassword.equals("90CgNvHrdxwQZVd4XUMELw==:0aba5Q6nTt6DxT84xxi5bA=="));
        return encryptedPassword;
    }

    static class secretKey {

        public static SecretKeySpec generateSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength)
                throws GeneralSecurityException {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
            SecretKey tempKey = keyFactory.generateSecret(keySpec);
            return new SecretKeySpec(tempKey.getEncoded(), "AES");
        }

        private static String base64Encoder(byte[] bytes) {
            return Base64.getEncoder().encodeToString(bytes);
        }

        public static String encryptPassword(String dataToEncrypt, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key);
            AlgorithmParameters parameters = pbeCipher.getParameters();
            IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
            byte[] cryptoText = pbeCipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
            byte[] iv = ivParameterSpec.getIV();
            return base64Encoder(iv) + ":" + base64Encoder(cryptoText);
        }
    }

}
