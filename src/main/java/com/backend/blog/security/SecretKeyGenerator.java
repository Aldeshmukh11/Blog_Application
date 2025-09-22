package com.backend.blog.security;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class SecretKeyGenerator {

	//Generate secret key
    public static String generateKey() throws NoSuchAlgorithmException {
    	// Use HmacSHA512 for HS512 algorithm
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
        keyGen.init(512); // 512 bits key
        SecretKey secretKey = keyGen.generateKey();

        // Encode to Base64
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Base64 Encoded Secret Key: " + base64Key);
        return base64Key;
    }
}
