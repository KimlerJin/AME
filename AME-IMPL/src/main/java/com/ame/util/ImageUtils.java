package com.ame.util;

import java.io.IOException;
import java.util.Base64;

public class ImageUtils {

    /**
     *      * 二进制流转Base64字符串      *      * @param data 二进制流      * @return data      * @throws IOException 异常      
     */
    public static String getImageString(byte[] data) throws IOException {
        return data != null ? Base64.getEncoder().encodeToString(data) : "";
    }

    /**
     *      * Base64字符串转 二进制流      *      * @param base64String Base64      * @return base64String      * @throws
     * IOException 异常      
     */
    public static byte[] getStringImage(String base64String) throws IOException {
        return base64String != null ? Base64.getDecoder().decode(base64String) : null;
    }
}
