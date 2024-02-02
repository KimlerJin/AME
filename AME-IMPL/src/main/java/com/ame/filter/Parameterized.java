package com.ame.filter;

import java.io.Serializable;
import java.util.List;

public interface Parameterized extends Serializable {

    List<Object> getParameters();

}
