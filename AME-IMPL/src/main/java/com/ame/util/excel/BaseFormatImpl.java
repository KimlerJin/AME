package com.ame.util.excel;

public class BaseFormatImpl<T> implements BaseColumnFormat<T> {

    @Override
    public String format(String col, T t) {
        return BeanUtils.getAttrValue(t, col);
    }

}
