package com.dans.javaonboard.authentication.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncryptionService {
    private final String ENCRYPTION_ALGORITHM = "AES";
    private final String ENCRYPTION_KEY = System.getenv("ENCRYPTION_KEY");

    public String encrypt(String plainText) {
        try {
            SecretKeySpec key = new SecretKeySpec(this.ENCRYPTION_KEY.getBytes(), this.ENCRYPTION_ALGORITHM);
            Cipher cipher = Cipher.getInstance(this.ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Encryption failed", e);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            SecretKeySpec key = new SecretKeySpec(this.ENCRYPTION_KEY.getBytes(), this.ENCRYPTION_ALGORITHM);
            Cipher cipher = Cipher.getInstance(this.ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decrypted);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Decryption failed", e);
        }

    }
}
