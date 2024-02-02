package com.ame.util.excel;

public interface BaseColumnFormat<T> {
    public String format(String col, T t);
}
