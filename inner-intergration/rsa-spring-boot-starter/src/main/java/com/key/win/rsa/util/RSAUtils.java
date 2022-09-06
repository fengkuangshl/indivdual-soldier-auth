package com.key.win.rsa.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RSAUtils {
    public static String privateKeyPath;        // replace your public key path here
    public static String publicKeyPath;     // replace your private path here

    @Value("${spring.rsa.private-key-path}")
    public void setPrivateKeyPath(String privateKeyPath) {
        RSAUtils.privateKeyPath = privateKeyPath;
    }

    @Value("${spring.rsa.public-key-path}")
    public void setPublicKeyPath(String publicKeyPath) {
        RSAUtils.publicKeyPath = publicKeyPath;
    }
}
