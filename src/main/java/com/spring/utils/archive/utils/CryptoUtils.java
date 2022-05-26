package com.spring.utils.archive.utils;

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
}
