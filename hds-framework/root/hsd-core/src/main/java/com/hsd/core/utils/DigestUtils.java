package com.hsd.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by werewolf on 2017/7/16.
 */
public class DigestUtils {

    public final static String ENCODING = "UTF-8";

    public static String MD5(String data) {
        return MD5(data, ENCODING);
    }


    public static String MD5(String string, String charset) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes(charset));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    public static String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);

    }

    public static byte[] decodeBase64(byte[] data) {
        return Base64.getDecoder().decode(data);
    }

    public static void main(String[] args) {
        System.out.println(MD5("123456"));
    }
}
