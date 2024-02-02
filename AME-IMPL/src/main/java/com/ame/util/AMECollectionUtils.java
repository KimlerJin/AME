package com.ame.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Tracy Date:2020/8/18 Description:
 */
public class AMECollectionUtils {

    /**
     * 查分list
     * 
     * @param values
     * @param size
     *            subList的大小
     * @return
     */
    public static List<List> splitList(List<? extends Object> values, int size) {
        List<List> result = new ArrayList<>();
        if (values.size() <= size) {
            result.add(values);
        } else {
            int count = 0;
            List<Object> subList = null;
            for (Object obj : values) {
                if (subList == null) {
                    subList = new ArrayList<>();
                    result.add(subList);
                }
                subList.add(obj);
                count++;
                if (count == size) {
                    count = 0;
                    subList = null;
                }
            }
        }
        return result;
    }

}
