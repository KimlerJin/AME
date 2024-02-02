package com.ame.util.excel;

/**
 * normal validata
 * 
 * @author karl_xu
 *
 * @param <T>
 */
public interface BaseRowPacker<T> {
    public T getInstance();

    /**
     * normal validata
     * 
     * @param obj
     * @param key
     * @param text
     * @return
     */
    public boolean packing(Object obj, String key, String text, int row);
}
