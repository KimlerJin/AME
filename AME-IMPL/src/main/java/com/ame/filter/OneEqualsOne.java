package com.ame.filter;

import java.util.Collections;
import java.util.List;

/**
 * author joe_jiang date 2019/10/29 19:57
 */
public class OneEqualsOne extends Criterion {
    private static final long serialVersionUID = 6450569338645474648L;

    @Override
    public String toHqlString(EntityQuery query) {
        return "1=1";
    }

    @Override
    public boolean hasCfField() {
        return false;
    }

    @Override
    public List<Object> getParameters() {
        return Collections.emptyList();
    }
}
