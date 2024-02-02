package com.ame.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpHeaderUtils {

    public static String generateContentDisposition(String fileName, String encodeType)
        throws UnsupportedEncodingException {
        String new_filename = URLEncoder.encode(fileName, encodeType);
        String header = "inline; filename=" + fileName + ";filename* = UTF-8''" + new_filename;
        return header;
    }
}
