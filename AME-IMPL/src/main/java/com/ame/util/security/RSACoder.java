package com.ame.util.security;


import com.ame.core.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public abstract class RSACoder {

    private static final Logger log = LoggerFactory.getLogger(RSACoder.class);

    private static final String RSA = "RSA";

    /**
     * 加密
     *
     * @param plainText
     *            明文数据
     * @param key
     *            密钥
     * @param keyType
     *            密钥类型
     * @return
     */
    public static byte[] encrypt(byte[] plainText, byte[] key, KeyType keyType) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            switch (keyType) {
                case PUBLIC:
                    cipher.init(Cipher.ENCRYPT_MODE,
                        KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(key)));
                    break;
                case PRIVATE:
                    cipher.init(Cipher.ENCRYPT_MODE,
                        KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(key)));
                    break;
                default:
                    break;
            }
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            log.error("", e);
            throw new PlatformException( "UnKnown Error");
        }
    }

    /**
     * 私钥解密过程
     *
     * @param cipherText
     *            私钥
     * @param key
     *            密文数据
     * @param keyType
     * @return
     */
    public static byte[] decrypt(byte[] cipherText, byte[] key, KeyType keyType) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            switch (keyType) {
                case PUBLIC:
                    cipher.init(Cipher.DECRYPT_MODE,
                        KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(key)));
                    break;
                case PRIVATE:
                    cipher.init(Cipher.DECRYPT_MODE,
                        KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(key)));
                    break;
                default:
                    break;
            }
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            throw new PlatformException( "UnKnown Error");
        }
    }

    public static KeyPair initKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(1024);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            log.error("", e);
            throw new PlatformException( "UnKnown Error");
        }
    }

    public enum KeyType {
        PUBLIC, PRIVATE
    }

}