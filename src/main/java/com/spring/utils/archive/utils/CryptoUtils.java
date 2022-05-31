package com.spring.utils.archive.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @apiNote Crypto utils
 * @author  Michelnajeho
 */
public class CryptoUtils {

    /**
     * MD5 encryption.
     *
     * @return MD5 ciphertext
     */
    public static String MD5encrypt(String plainText) {

        String MD5 = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(plainText.getBytes());

            byte[] byteData = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();

            for (byte byteDatum : byteData) {
                stringBuilder.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            MD5 = stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return MD5;
    }

    /**
     * SHA256 encryption.
     *
     * @return SHA256 ciphertext
     */
    public static String SHA256encryptor(String plainText) {

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
