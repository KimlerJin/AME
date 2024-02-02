package com.ame.filter;

import com.ame.constant.CommonConstants;
import com.ame.core.RequestInfo;
import com.ame.pagination.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

public class EntityFilter implements Serializable, IFilter {
    /**
     *
     */
    private static final long serialVersionUID = 4543881641440981065L;

    private EntityQuery entityQuery;

    public EntityFilter() {
    }

    public EntityFilter(Class<?> entityClass, boolean ignoreDataPermission) {
        entityQuery = new EntityQuery(entityClass);
        if (!ignoreDataPermission) {
            addDataPermission();
        }
    }

    public EntityFilter(Class<?> entityClass) {
        this(entityClass, false);
    }

    public EntityFilter setCacheable(boolean isCacheable) {
        entityQuery.setCacheable(isCacheable);
        return this;
    }

    public EntityFilter orderBy(String key, boolean isDesc) {
        if (isDesc) {
            entityQuery.addOrder(Order.desc(key, false));
        } else {
            entityQuery.addOrder(Order.asc(key, false));
        }
        return this;
    }

    public EntityFilter cfOrderBy(String key, boolean isDesc) {
        if (isDesc) {
            entityQuery.addOrder(Order.desc(key, true));
        } else {
            entityQuery.addOrder(Order.asc(key, true));
        }
        return this;
    }

    public EntityFilter orderBySkipNull(String key, boolean isDesc) {
        if (StringUtils.isNotEmpty(key)) {
            orderBy(key, isDesc);
        }
        return this;
    }

    public EntityFilter addOrder(Order order) {
        entityQuery.addOrder(order);
        return this;
    }

    public EntityFilter resetOrder() {
        entityQuery.resetOrder();
        return this;
    }

    public EntityFilter fieldIsNull(String key, boolean isNull) {
        if (isNull) {
            entityQuery.add(HqlRestrictions.isNull(key, false));
        } else {
            entityQuery.add(HqlRestrictions.isNotNull(key, false));
        }
        return this;
    }

    public EntityFilter cfFieldIsNull(String key, boolean isNull) {
        if (isNull) {
            entityQuery.add(HqlRestrictions.isNull(key, true));
        } else {
            entityQuery.add(HqlRestrictions.isNotNull(key, true));
        }
        return this;
    }

    public EntityFilter fieldEqualTo(String key, Object value) {
        entityQuery.add(HqlRestrictions.equals(key, false, value));
        return this;
    }

    /**
     * 如果传入的过滤条件为空对象或空字符串，则该过滤条件将不会构建在查询语句中
     *
     * @param key
     * @param value
     * @return
     */
    public EntityFilter fieldEqualToSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            fieldEqualTo(key, value);
        }
        return this;
    }

    public EntityFilter cfFieldEqualTo(String key, Object value) {
        entityQuery.add(HqlRestrictions.equals(key, true, value));
        return this;
    }

    public EntityFilter cfFieldEqualToSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            cfFieldEqualTo(key, value);
        }
        return this;
    }

    public EntityFilter fieldNotEqualTo(String key, Object value) {
        entityQuery.add(HqlRestrictions.notEquals(key, false, value));
        return this;
    }

    public EntityFilter fieldNotEqualToSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            fieldNotEqualTo(key, value);
        }
        return this;
    }

    public EntityFilter cfFieldNotEqualTo(String key, Object value) {
        entityQuery.add(HqlRestrictions.notEquals(key, true, value));
        return this;
    }

    public EntityFilter cfFieldNotEqualToSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            cfFieldNotEqualTo(key, value);
        }
        return this;
    }

    public EntityFilter fieldGreatorOrEqualTo(String key, Object value) {
        entityQuery.add(HqlRestrictions.greatorEquals(key, false, value));
        return this;
    }

    public EntityFilter fieldGreatorOrEqualToSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            fieldGreatorOrEqualTo(key, value);
        }
        return this;
    }

    public EntityFilter cfFieldGreatorOrEqualTo(String key, Object value) {
        entityQuery.add(HqlRestrictions.greatorEquals(key, true, value));
        return this;
    }

    public EntityFilter cfFieldGreatorOrEqualToSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            cfFieldGreatorOrEqualTo(key, value);
        }
        return this;
    }

    public EntityFilter fieldGreatorThan(String key, Object value) {
        entityQuery.add(HqlRestrictions.greator(key, false, value));
        return this;
    }

    public EntityFilter fieldGreatorThanSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            fieldGreatorThan(key, value);
        }
        return this;
    }

    public EntityFilter cfFieldGreatorThan(String key, Object value) {
        entityQuery.add(HqlRestrictions.greator(key, true, value));
        return this;
    }

    public EntityFilter cfFieldGreatorThanSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            cfFieldGreatorThan(key, value);
        }
        return this;
    }

    public EntityFilter fieldLessOrEqualTo(String key, Object value) {
        entityQuery.add(HqlRestrictions.lessEquals(key, false, value));
        return this;
    }

    public EntityFilter fieldLessOrEqualToSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            fieldLessOrEqualTo(key, value);
        }
        return this;
    }

    public EntityFilter cfFieldLessOrEqualTo(String key, Object value) {
        entityQuery.add(HqlRestrictions.lessEquals(key, true, value));
        return this;
    }

    public EntityFilter cfFieldLessOrEqualToSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            cfFieldLessOrEqualTo(key, value);
        }
        return this;
    }

    public EntityFilter fieldLessThan(String key, Object value) {
        entityQuery.add(HqlRestrictions.less(key, false, value));
        return this;
    }

    public EntityFilter fieldLessThanSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            fieldLessThan(key, value);
        }
        return this;
    }

    public EntityFilter cfFieldLessThan(String key, Object value) {
        entityQuery.add(HqlRestrictions.less(key, true, value));
        return this;
    }

    public EntityFilter cfFieldLessThanSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            cfFieldLessThan(key, value);
        }
        return this;
    }

    public EntityFilter fieldContains(String key, Object value) {
        if (value instanceof String) {
            entityQuery.add(HqlRestrictions.like(key, false, (String) value));
        } else {
            // TODO
        }
        return this;
    }

    public EntityFilter fieldContains(String key, Object value, MatchMode mode) {
        if (value instanceof String) {
            entityQuery.add(HqlRestrictions.like(key, false, (String) value, mode));
        } else {
            // TODO
        }
        return this;
    }

    public EntityFilter fieldContainsSkipNull(String key, Object value, MatchMode mode) {
        if (nonNullAndNotEmpty(value)) {
            fieldContains(key, value, mode);
        }
        return this;
    }

    public EntityFilter fieldContainsSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            fieldContains(key, value);
        }
        return this;
    }

    public EntityFilter cfFieldContains(String key, Object value) {
        if (value instanceof String) {
            entityQuery.add(HqlRestrictions.like(key, true, (String) value));
        } else {
            // TODO
        }
        return this;
    }

    public EntityFilter cfFieldContainsSkipNull(String key, Object value) {
        if (nonNullAndNotEmpty(value)) {
            cfFieldContains(key, value);
        }
        return this;
    }

    public EntityFilter cfFieldContains(String key, Object value, MatchMode mode) {
        if (value instanceof String) {
            this.entityQuery.add(HqlRestrictions.like(key, true, (String) value, mode));
        }

        return this;
    }


    public EntityFilter fieldIn(String key, List<?> values) {
        entityQuery.add(HqlRestrictions.in(key, false, values));
        return this;
    }

    public EntityFilter fieldInSkipNull(String key, List<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            fieldIn(key, values);
        }
        return this;
    }

    public EntityFilter fieldNotIn(String key, List<?> values) {
        entityQuery.add(HqlRestrictions.notIn(key, false, values));
        return this;
    }

    public EntityFilter fieldNotInSkipNull(String key, List<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            fieldNotIn(key, values);
        }
        return this;
    }

    public EntityFilter cfFieldIn(String key, List<?> values) {
        entityQuery.add(HqlRestrictions.in(key, true, values));
        return this;
    }

    public EntityFilter cfFieldInSkipNull(String key, List<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            cfFieldIn(key, values);
        }
        return this;
    }

    public EntityFilter cfFieldNotIn(String key, List<?> values) {
        entityQuery.add(HqlRestrictions.notIn(key, true, values));
        return this;
    }

    public EntityFilter cfFieldNotInSkipNull(String key, List<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            cfFieldNotIn(key, values);
        }
        return this;
    }

    public EntityFilter fieldLeftLike(String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            entityQuery.add(HqlRestrictions.like(key, false, value, MatchMode.START));
        }
        return this;
    }

    public EntityFilter fieldRightLike(String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            entityQuery.add(HqlRestrictions.like(key, false, value, MatchMode.END));
        }
        return this;
    }

    public EntityFilter cfFieldLeftLike(String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            entityQuery.add(HqlRestrictions.like(key, true, value, MatchMode.START));
        }
        return this;
    }

    public EntityFilter cfFieldRightLike(String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            entityQuery.add(HqlRestrictions.like(key, true, value, MatchMode.END));
        }
        return this;
    }

    public EntityFilter setPageInfo(PageInfo pageInfo) {
        entityQuery.setPageInfo(pageInfo);
        return this;
    }

    public EntityFilter setMaxResult(int maxResult) {
        if (entityQuery.getPageInfo() != null) {
            entityQuery.getPageInfo().setPageSize(maxResult);
            return this;
        } else {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageSize(maxResult);
            entityQuery.setPageInfo(pageInfo);
            return this;
        }
    }

    public EntityFilter setStartPosition(int startPosition) {
        if (entityQuery.getPageInfo() != null) {
            entityQuery.getPageInfo().setStartPosition(startPosition);
            return this;
        } else {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setStartPosition(startPosition);
            entityQuery.setPageInfo(pageInfo);
            return this;
        }
    }

    public EntityFilter add(Criterion criterion) {
        entityQuery.add(criterion);
        return this;
    }

    /**
     * 添加数据权限
     *
     * @return
     */
    private EntityFilter addDataPermission() {

        List<String> dataPermissionList = RequestInfo.current().getDataPermissionList();
        List<Criterion> criterias = new ArrayList<>();
        criterias.add(new OneEqualsTwo());
        if (dataPermissionList != null && dataPermissionList.size() > 0) {
            dataPermissionList.forEach(permission -> {
                criterias.add(HqlRestrictions.like(CommonConstants.DATA_PERMISSION_PREDICATE, false, permission,
                        MatchMode.START));
            });
        }
        criterias.add(HqlRestrictions.isNull(CommonConstants.DATA_PERMISSION_PREDICATE, false));
        criterias.add(HqlRestrictions.equals(CommonConstants.DATA_PERMISSION_PREDICATE, false, ""));
        entityQuery.add(HqlRestrictions.or(criterias.stream().toArray(Criterion[]::new)));

        return this;
    }

    public Class<?> getEntityClass() {
        return entityQuery.getEntityClass();
    }

    public EntityQuery getEntityQuery() {
        return entityQuery;
    }

    public void setEntityQuery(EntityQuery entityQuery) {
        this.entityQuery = entityQuery;
    }


    private static boolean nonNullAndNotEmpty(Object value) {
        if (Objects.nonNull(value)) {
            if (value instanceof String) {
                return StringUtils.isNotEmpty((CharSequence) value);
            } else if (value instanceof Collection<?>) {
                return !((Collection<?>) value).isEmpty();
            } else {
                return true;
            }
        }
        return false;
    }

    public EntityFilter appendQuery(EntityFilter origin) {
        if (origin != null) {
            origin.getEntityQuery().getCriterions()
                    .forEach(this::add);
        }
        return this;
    }
}
