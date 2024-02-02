package com.ame.util;

import java.util.Deque;
import java.util.LinkedList;

public class NumberRadixUtils {

    protected static final char[] FLAGS_34 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
        'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static String to34(long a) {
        Deque<Character> temp = new LinkedList<>();
        long c = a % 34;
        temp.push(FLAGS_34[(int)c]);
        while (a / 34 > 0) {
            a = a / 34;
            c = a % 34;
            temp.push(FLAGS_34[(int)c]);
        }
        StringBuilder s = new StringBuilder();
        while (!temp.isEmpty()) {
            s.append(temp.pop());
        }
        return s.toString();
    }

    private static String toRadix(long a, int radix) {
        return Long.toString(a, radix);
    }

}
