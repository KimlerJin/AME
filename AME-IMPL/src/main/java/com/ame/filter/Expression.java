package com.ame.filter;

import java.io.Serializable;

public interface Expression extends Serializable {

    String toHqlString(EntityQuery query);

    boolean hasCfField();

}
