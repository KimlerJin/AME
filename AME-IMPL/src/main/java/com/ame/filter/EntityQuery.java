package com.ame.filter;


import com.ame.annotation.IgnoreEntityQuery;
import com.ame.pagination.PageInfo;
import jakarta.persistence.JoinColumn;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityQuery implements Criteria {

    private static final long serialVersionUID = -3014099545412401812L;
    private static String DEFAULT_ROOT_ALIAS = "d_root";
    private static String DEFAULT_EXTENSION_ALIAS = "d_extension";
    protected String rootAlies = DEFAULT_ROOT_ALIAS;
    protected String extensionAlies = DEFAULT_EXTENSION_ALIAS;
    protected int parameterPosition = 1;
    private String entityName;
    private Class<?> entityClass;
    private List<Criterion> criterions = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private PageInfo pageInfo;
    private boolean isCacheable;

    public EntityQuery() {}

    public EntityQuery(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.entityName = entityClass.getName();
    }

    public EntityQuery(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public Criteria add(Criterion criterion) {
        criterion.setEntityQuery(this);
        criterions.add(criterion);
        return this;
    }

    @Override
    public Criteria addOrder(Order order) {
        orders.remove(order);
        orders.add(order);
        return this;
    }

    @Override
    public Criteria resetOrder() {
        orders.clear();
        return this;
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    @Override
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String generateHql(boolean isJoinFetch) {
        parameterPosition = 1;
        StringBuilder hql = new StringBuilder();
        hql.append(generateHQLFromStatement(false, isJoinFetch)).append(generateHQLWhereStatement())
            .append(generateHQLOrderStatement());
        return hql.toString();
    }

    public String generateHqlCount() {
        parameterPosition = 1;
        StringBuilder hql = new StringBuilder();
        hql.append("SELECT COUNT(*) " + generateHQLFromStatement(true, false)).append(generateHQLWhereStatement());
        return hql.toString();
    }

    public List<Object> getParameters() {
        List<Object> objects = new ArrayList<>();
        for (Criterion criterion : criterions) {
            objects.addAll(criterion.getParameters());
        }
        return objects;
    }

    private String generateHQLFromStatement(boolean isCountHql, boolean isJoinFetch) {
        StringBuilder tableStatement = new StringBuilder(" from ");
        tableStatement.append(entityName).append(" ").append(rootAlies).append(" ");
        if (!isCountHql && entityClass != null && isJoinFetch) {
            Field[] allFields = FieldUtils.getAllFields(entityClass);
            for (Field field : allFields) {
                if (field.getAnnotation(JoinColumn.class) != null
                    && field.getAnnotation(IgnoreEntityQuery.class) == null) {
                    tableStatement.append(" join fetch ").append(rootAlies).append(".").append(field.getName())
                        .append(" d_").append(field.getName());
                }
            }
        }
        return tableStatement.toString();
    }

    private String generateHQLWhereStatement() {
        StringBuilder whereStatement = new StringBuilder(" where 1=1 ");
        for (Criterion criterion : criterions) {
            whereStatement.append(" and ").append(criterion.toHqlString(this));
        }
        return whereStatement.toString();
    }

    private String generateHQLOrderStatement() {
        if (orders.size() > 0) {
            StringBuilder orderStatement = new StringBuilder(" order by ");
            for (int i = 0; i < orders.size(); i++) {
                orderStatement.append(orders.get(i).toHqlString(this));
                if (i < orders.size() - 1) {
                    orderStatement.append(", ");
                }
            }
            return orderStatement.toString();
        }
        return "";
    }


    public List<Order> getOrders() {
        return orders;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public boolean isCacheable() {
        return isCacheable;
    }

    public void setCacheable(boolean cacheable) {
        isCacheable = cacheable;
    }

    public String getEntityName() {
        return entityName;
    }

    public boolean isUsingExtensionTable() {
        for (Criterion criterion : criterions) {
            if (criterion.hasCfField()) {
                return true;
            }
        }
        for (Order order : orders) {
            if (order.hasCfField()) {
                return true;
            }
        }
        return false;
    }
}
