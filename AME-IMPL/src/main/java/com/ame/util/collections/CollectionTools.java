package com.ame.util.collections;



import com.google.common.collect.Lists;

import java.util.List;

/**
 * 集合操作工具类
 *
 * @author evan_yang
 */
public final class CollectionTools {

    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list
     * @param len
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, final int len) {
        if (list == null || list.isEmpty() || len < 1) {
            return null;
        }
        List<List<T>> result = Lists.newArrayList();
        final int size = list.size();
        final int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }

}
