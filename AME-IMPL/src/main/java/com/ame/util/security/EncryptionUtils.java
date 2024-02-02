package com.ame.util.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.StringTokenizer;

interface CipherFactory {
    Cipher getEncryptionCipher();

    Cipher getDecryptionCipher();
}

/**
 * @author king_sun
 *
 */
public class EncryptionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtils.class);
    /***********************************************************************/

    private static final CipherFactory cipherFactory = new PBECipherFactory();

    public static void main(String[] args) {

        System.out.println(encrypt("admin", false));
    }

    public static String getEncryptedPassword(String password, boolean reversible) {
        if (reversible) {
            byte[] clearText = password.getBytes();
            byte[] cipherText = encrypt(clearText);
            return toString(cipherText);
        } else {
            return jcrypt.newCrypt("E1", password);
        }
    }

    public static String getPlainTextPassword(String reversibleEncryptedPassword) {
        byte[] cipherText = toBytes(reversibleEncryptedPassword);
        byte[] clearText = decrypt(cipherText);
        return new String(clearText);
    }

    public static String encrypt(String text, boolean reversible) {
        return getEncryptedPassword(text, reversible);
    }

    private static byte[] encrypt(byte[] clearText) {
        Cipher c = cipherFactory.getEncryptionCipher();
        byte[] cipherText = dofinal(clearText, c);
        return cipherText;
    }

    private static byte[] decrypt(byte[] cipherText) {
        Cipher c = cipherFactory.getDecryptionCipher();
        byte[] clearText = dofinal(cipherText, c);
        return clearText;
    }

    private static String toString(byte[] text) {
        StringBuffer b = new StringBuffer();
        int l = text.length;
        for (int i = 0; i < l; i++) {
            if (i > 0) {
                b.append(",");
            }
            b.append(new Integer(text[i]));
        }
        return b.toString();
    }

    private static byte[] toBytes(String text) {
        StringTokenizer st = new StringTokenizer(text, ",");
        int l = st.countTokens();
        byte[] out = new byte[l];
        for (int i = 0; i < l; i++) {
            String t = st.nextToken();
            byte b = new Byte(t).byteValue();
            out[i] = b;
        }
        return out;
    }

    private static byte[] dofinal(byte[] in, Cipher c) {
        int l = in.length;
        byte[] out = new byte[l];
        System.arraycopy(in, 0, out, 0, l);

        // Encrypt the cleartext
        try {
            out = c.doFinal(in);
        } catch (IllegalStateException e) {
            e.printStackTrace(System.out);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace(System.out);
        } catch (BadPaddingException e) {
            e.printStackTrace(System.out);
        }
        return out;
    }

    public static String MD5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            LOGGER.error("", e);
            return "";
        }

        byte[] byteArray = inStr.getBytes(StandardCharsets.UTF_8);
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int)md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String SHAEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            LOGGER.error("", e);
            return "";
        }

        byte[] byteArray = inStr.getBytes(StandardCharsets.UTF_8);
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int)md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}

class PBECipherFactory implements CipherFactory {
    private static Cipher ec = null;
    private static Cipher dc = null;
    private String password = " jforkfd$#";

    // Salt
    private byte[] salt =
        {(byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c, (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99};

    // Iteration count
    private int count = 20;
    private SecretKey pbeKey = null;
    private PBEParameterSpec pbeParamSpec = null;

    public Cipher getEncryptionCipher() {
        if (null == ec) {
            ec = getCipher(Cipher.ENCRYPT_MODE);
        }
        return ec;
    }

    public Cipher getDecryptionCipher() {
        if (null == dc) {
            dc = getCipher(Cipher.DECRYPT_MODE);
        }
        return dc;
    }

    private Cipher getCipher(int mode) {
        try {
            // Create PBE Cipher
            Cipher c = Cipher.getInstance("PBEWithMD5AndDES");

            // Initialize PBE Cipher with key and parameters
            c.init(mode, getPBEKey(), getPBEParamSpec());
            return c;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new RuntimeException(e.getMessage());
        }
    }

    private SecretKey getPBEKey() {
        // pbeKey = null;
        if (null == pbeKey) {
            try {
                PBEKeySpec pbeKeySpec;
                SecretKeyFactory keyFac;
                keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
                pbeKeySpec = new PBEKeySpec(password.toCharArray());
                pbeKey = keyFac.generateSecret(pbeKeySpec);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace(System.out);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace(System.out);
            }
        }
        return pbeKey;
    }

    private PBEParameterSpec getPBEParamSpec() {
        // pbeParamSpec = null;
        if (null == pbeParamSpec) {
            // Create PBE parameter set
            pbeParamSpec = new PBEParameterSpec(salt, count);
        }
        return pbeParamSpec;
    }
}
