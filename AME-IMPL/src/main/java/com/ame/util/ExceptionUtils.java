package com.ame.util;

public class ExceptionUtils {

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> E getCauseByClass(Throwable exception, Class<E> exceptionClass) {
        if (exception == null) {
            return null;
        }
        if (exceptionClass.isInstance(exception)) {
            return (E)exception;
        }
        return getCauseByClass(exception.getCause(), exceptionClass);
    }

}
