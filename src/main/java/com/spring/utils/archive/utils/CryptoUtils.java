package com.spring.utils.archive.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

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

    /**
     * AES256 encrypt
     *
     * Algorithm AES PBKDF2WithHmacSHA1
     * Algorithm mode CBC (Cipher Block Chaining Mode)
     * Iteration count 70000
     * Key length 256
     *
     * @return AES256 ciphertext
     */
    public static String AES256encrypt(String plainText, String privateKey) throws Exception {

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(privateKey.toCharArray(), bytes, 70000, 256);

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();

        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] buffer = new byte[bytes.length + ivBytes.length + encryptedTextBytes.length];
        System.arraycopy(bytes, 0, buffer, 0, bytes.length);
        System.arraycopy(ivBytes, 0, buffer, bytes.length, ivBytes.length);
        System.arraycopy(encryptedTextBytes, 0, buffer, bytes.length + ivBytes.length, encryptedTextBytes.length);

        return Base64.getEncoder().encodeToString(buffer);
    }

    /**
     * AES256 decrypt
     *
     * Algorithm AES PBKDF2WithHmacSHA1
     * Algorithm mode CBC (Cipher Block Chaining Mode)
     * Iteration count 70000
     * Key length 256
     *
     * @return plaintext
     */
    public static String AES256decrypt(String ciphertext, String privateKey) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(ciphertext));

        byte[] saltBytes = new byte[20];
        buffer.get(saltBytes, 0, saltBytes.length);

        byte[] ivBytes = new byte[cipher.getBlockSize()];
        buffer.get(ivBytes, 0, ivBytes.length);

        byte[] encryoptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
        buffer.get(encryoptedTextBytes);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(privateKey.toCharArray(), saltBytes, 70000, 256);

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
        byte[] decryptedTextBytes = cipher.doFinal(encryoptedTextBytes);

        return new String(decryptedTextBytes);
    }
}
