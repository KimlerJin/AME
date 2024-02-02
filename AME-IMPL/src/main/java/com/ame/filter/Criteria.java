package com.ame.filter;


import com.ame.pagination.PageInfo;

import java.io.Serializable;

public interface Criteria extends Serializable {

    Criteria add(Criterion criterion);

    Criteria addOrder(Order order);

    Criteria resetOrder();

    void setPageInfo(PageInfo pageInfo);

}
