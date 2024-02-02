package com.ame.utils;



import java.io.UnsupportedEncodingException;

/**
 * java自带有一个 java.net.URLDecoder和java.net.URLEncoder。
 *
 * 通过这两个类，可以调用encode()或者decode()方法对字符串进行URL编码。
 *
 * 那既然有了，为什么还要自己实现一套呢？主要原因是Jdk中并没有提供encodeURIComponent和decodeURIComponent的方法。
 *
 * 这两个方法作用其实跟encode()和decode()基本相似。区别主要是，在java中，url编码时，会把空格转换成+号。而某些非java语言实现的客户端一般空格转义出来是 %20 ，这样就容易发生decode不出这个空格的问题。比如IOS中，会把这个+直接显示了，而不是转义成空格。这就跟我们想要的结果违背了。比如js中就自带有encodeURIComponent和decodeURIComponent的方法。
 */

public class URIEncoderUtils {
    public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

    /**
     * Description:
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     * @see
     */
    public static String encodeURI(String str)
            throws UnsupportedEncodingException {
        String isoStr = new String(str.getBytes("UTF8"), "ISO-8859-1");
        char[] chars = isoStr.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] <= 'z' && chars[i] >= 'a') || (chars[i] <= 'Z' && chars[i] >= 'A')
                    || chars[i] == '-' || chars[i] == '_' || chars[i] == '.' || chars[i] == '!'
                    || chars[i] == '~' || chars[i] == '*' || chars[i] == '\'' || chars[i] == '('
                    || chars[i] == ')' || chars[i] == ';' || chars[i] == '/' || chars[i] == '?'
                    || chars[i] == ':' || chars[i] == '@' || chars[i] == '&' || chars[i] == '='
                    || chars[i] == '+' || chars[i] == '$' || chars[i] == ',' || chars[i] == '#'
                    || (chars[i] <= '9' && chars[i] >= '0')) {
                sb.append(chars[i]);
            } else {
                sb.append("%");
                sb.append(Integer.toHexString(chars[i]));
            }
        }
        return sb.toString();
    }

    /**
     * Description:
     *
     * @param input
     * @return
     * @see
     */
    public static String encodeURIComponent(String input) {
        if (null == input || "".equals(input.trim())) {
            return input;
        }

        int l = input.length();
        StringBuilder o = new StringBuilder(l * 3);
        try {
            for (int i = 0; i < l; i++) {
                String e = input.substring(i, i + 1);
                if (ALLOWED_CHARS.indexOf(e) == -1) {
                    byte[] b = e.getBytes("utf-8");
                    o.append(getHex(b));
                    continue;
                }
                o.append(e);
            }
            return o.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return input;
    }

    private static String getHex(byte buf[]) {
        StringBuilder o = new StringBuilder(buf.length * 3);
        for (int i = 0; i < buf.length; i++) {
            int n = (int) buf[i] & 0xff;
            o.append("%");
            if (n < 0x10) {
                o.append("0");
            }
            o.append(Long.toString(n, 16).toUpperCase());
        }
        return o.toString();
    }
}
