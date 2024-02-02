package com.ame.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 常用加密算法工具类
 *
 * @author henry_deng
 */
public class EncryptUtils {

    /**
     * 用MD5算法进行加密
     *
     * @param str
     *            需要加密的字符串
     * @return MD5加密后的结果
     */
    public static String encodeMD5String(String str) {
        return encode(str, "MD5");
    }

    /**
     * 用SHA算法进行加密
     *
     * @param str
     *            需要加密的字符串
     * @return SHA加密后的结果
     */
    public static String encodeSHAString(String str) {
        return encode(str, "SHA");
    }

    /**
     * 用base64算法进行加密
     *
     * @param str
     *            需要加密的字符串
     * @return base64加密后的结果
     */
    public static String encodeBase64String(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * 用base64算法进行解密
     *
     * @param str
     *            需要解密的字符串
     * @return base64解密后的结果
     * @throws IOException
     */
    public static String decodeBase64String(String str) throws IOException {
        return new String(Base64.getDecoder().decode(str));
    }

    private static String encode(String str, String method) {
        MessageDigest md = null;
        String dstr = null;
        try {
            md = MessageDigest.getInstance(method);
            byte[] e = md.digest(str.getBytes());
            dstr = toHexString(e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        return dstr;
    }

    private static String toHexString(byte bytes[]) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; n < bytes.length; n++) {
            stmp = Integer.toHexString(bytes[n] & 0xff);
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }

        return hs.toString();
    }

    // public static void main(String[] args){
    // System.out.println(encodeMD5String("123456"));
    // }
}