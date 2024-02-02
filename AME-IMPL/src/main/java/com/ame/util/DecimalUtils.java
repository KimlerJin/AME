package com.ame.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalUtils {

    public static final String DECIMAL_2 = "#.##";
    public static final String DECIMAL_5 = "#.#####";

    public static String format(Number num, String format) {
        return null == num ? "" : new DecimalFormat(format).format(num);
    }

    public static boolean equalsZero(BigDecimal num) {
        return num.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean greatorZero(BigDecimal num) {
        return num.compareTo(BigDecimal.ZERO) == 1;
    }

    public static boolean greatorOrEqualsZero(BigDecimal num) {
        return num.compareTo(BigDecimal.ZERO) >= 0;
    }

    public static boolean equalsOne(BigDecimal num) {
        return num.compareTo(BigDecimal.ONE) == 0;
    }

    public static boolean greatorOne(BigDecimal num) {
        return num.compareTo(BigDecimal.ONE) == 1;
    }

    public static boolean greatorOrEqualsOne(BigDecimal num) {
        return num.compareTo(BigDecimal.ONE) >= 0;
    }

    // public static void main(String[] args) {
    //
    // }

}