package com.ame.util.security;


import com.ame.core.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public abstract class AESCoder {

    private static final Logger log = LoggerFactory.getLogger(AESCoder.class);

    private static final String KEY_ALGORITHM = "AES";

    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static byte[] encrypt(byte[] plainText, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            log.error("", e);
            throw new PlatformException( "UnKnown Error");
        }
    }

    public static byte[] decrypt(byte[] ciphertext, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(ciphertext);
        } catch (Exception e) {
            log.error("", e);
            throw new PlatformException( "UnKnown Error");
        }
    }

    public static byte[] initKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(128);
            SecretKey generateKey = keyGenerator.generateKey();
            return generateKey.getEncoded();
        } catch (Exception e) {
            log.error("", e);
            throw new PlatformException( "UnKnown Error");
        }
    }

}
