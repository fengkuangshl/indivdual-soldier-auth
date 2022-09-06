package com.key.win.rsa.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

public class SignUtils {

    private static final String CHARSET = "UTF-8";

    public static String sha(String raw) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(raw.getBytes(CHARSET));
        return Hex.encodeHexString(messageDigest.digest());
    }
}
