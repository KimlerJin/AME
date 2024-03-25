package com.ame.hibernate;

import com.ame.core.RequestInfo;
import com.ame.entity.IBaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.apache.commons.lang3.StringUtils;


import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

public class AMEEntityListener {

    /**
     *
     */
    private static final long serialVersionUID = 3940006209141203195L;

    public static void setEntityProperties(IBaseEntity baseEntity) {
        baseEntity.setCreateBid(-1);
        baseEntity.setModifyBid(-1);

        RequestInfo current = RequestInfo.current();
        if (current != null) {
            baseEntity.setCreateIp(current.getUserIpAddress());
            baseEntity.setLastModifyIp(current.getUserIpAddress());
            if (baseEntity.getCreateUserId() <= 0) {
                baseEntity.setCreateUserId(current.getUserId());
                baseEntity.setCreateUserName(current.getUserName());
                baseEntity.setCreateUserFullName((current.getUserFirstName() == null ? "" : current.getUserFirstName())
                    + (current.getUserLastName() == null ? "" : current.getUserLastName()));
            }
            if (baseEntity.getCreateTime() == null) {
                baseEntity.setCreateTime(LocalDateTime.now());
            }
            if (baseEntity.getLastModifyUserId() <= 0) {
                baseEntity.setLastModifyUserId(current.getUserId());
                baseEntity.setLastModifyUserName(current.getUserName());
                baseEntity
                    .setLastModifyUserFullName((current.getUserFirstName() == null ? "" : current.getUserFirstName())
                        + (current.getUserLastName() == null ? "" : current.getUserLastName()));
            }
            if (baseEntity.getLastModifyTime() == null) {
                baseEntity.setLastModifyTime(LocalDateTime.now());
            }
            if (StringUtils.isNotEmpty(baseEntity.getDataPermissionPredicate())) {
                baseEntity.setDataPermissionPredicate(current.getDataPermissionCode());
            }
        } else {
            LocalDateTime now = LocalDateTime.now();
            if (baseEntity.getCreateTime() == null) {
                baseEntity.setCreateTime(now);
            }
            if (baseEntity.getLastModifyTime() == null) {
                baseEntity.setLastModifyTime(now);
            }
        }

    }

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof IBaseEntity) {
            setEntityProperties((IBaseEntity)entity);
        } else if (entity instanceof Map) {
//            preCustomObjectSavedOrMerged((Map<String, Object>)entity);
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof IBaseEntity) {
            setPreUpdateEntityProperties((IBaseEntity)entity);
        } else if (entity instanceof Map) {
//            preCustomObjectSavedOrMerged((Map<String, Object>)entity);
        }
    }

    public static void setPreUpdateEntityProperties(IBaseEntity baseEntity) {
        baseEntity.setModifyBid(-1);
        baseEntity.setRowLogId("-1");

        RequestInfo current = RequestInfo.current();
        if (current != null) {
            baseEntity.setLastModifyUserId(current.getUserId());
            baseEntity.setLastModifyUserName(current.getUserName());
            baseEntity.setLastModifyUserFullName((current.getUserFirstName() == null ? "" : current.getUserFirstName())
                    + (current.getUserLastName() == null ? "" : current.getUserLastName()));
            baseEntity.setLastModifyIp(current.getUserIpAddress());
            baseEntity.setLastModifyTime(LocalDateTime.now());
        } else {
            baseEntity.setLastModifyTime(LocalDateTime.now());
        }

        // 判断current是否为空
    }



//    private void preCustomObjectSavedOrMerged(Map<String, Object> dynamicEntity) {
//        dynamicEntity.put(DynamicEntityColumnName.DTS_MODIFIED_BID.getColumnName(), -1L);
//        dynamicEntity.put(DynamicEntityColumnName.DTS_CREATION_BID.getColumnName(), -1L);
//        if (!dynamicEntity.containsKey(DynamicEntityColumnName.IS_DELETED.getColumnName())) {
//            dynamicEntity.put(DynamicEntityColumnName.IS_DELETED.getColumnName(), 0L);
//        }
//        RequestInfo current = RequestInfo.current();
//        if (current != null) {
//            dynamicEntity.put(DynamicEntityColumnName.CREATE_IP.getColumnName(), current.getUserIpAddress());
//            dynamicEntity.put(DynamicEntityColumnName.LM_IP.getColumnName(), current.getUserIpAddress());
//
//            if (!dynamicEntity.containsKey(DynamicEntityColumnName.CREATE_USER_ID.getColumnName())) {
//                dynamicEntity.put(DynamicEntityColumnName.CREATE_USER_ID.getColumnName(), current.getUserId());
//                dynamicEntity.put(DynamicEntityColumnName.CREATE_USER_NAME.getColumnName(), current.getUserName());
//                dynamicEntity.put(DynamicEntityColumnName.CREATE_USER_FULL_NAME.getColumnName(),
//                    (current.getUserFirstName() == null ? "" : current.getUserFirstName())
//                        + (current.getUserLastName() == null ? "" : current.getUserLastName()));
//            }
//            if (!dynamicEntity.containsKey(DynamicEntityColumnName.CREATE_TIME.getColumnName())) {
//                dynamicEntity.put(DynamicEntityColumnName.CREATE_TIME.getColumnName(),
//                    current.getRequestZonedDateTime());
//            }
//            if (!dynamicEntity.containsKey(DynamicEntityColumnName.LM_USER_ID.getColumnName())) {
//                dynamicEntity.put(DynamicEntityColumnName.LM_USER_ID.getColumnName(), current.getUserId());
//                dynamicEntity.put(DynamicEntityColumnName.LM_USER_NAME.getColumnName(), current.getUserName());
//                dynamicEntity.put(DynamicEntityColumnName.LM_USER_FULL_NAME.getColumnName(),
//                    (current.getUserFirstName() == null ? "" : current.getUserFirstName())
//                        + (current.getUserLastName() == null ? "" : current.getUserLastName()));
//            }
//            if (!dynamicEntity.containsKey(DynamicEntityColumnName.LM_TIME.getColumnName())) {
//                dynamicEntity.put(DynamicEntityColumnName.LM_TIME.getColumnName(), current.getRequestZonedDateTime());
//            }
//            if (!dynamicEntity.containsKey(DynamicEntityColumnName.DATA_PERMISSION_PREDICATE.getColumnName())) {
//                dynamicEntity.put(DynamicEntityColumnName.DATA_PERMISSION_PREDICATE.getColumnName(),
//                    current.getDataPermissionCode());
//            }
//        } else {
//            ZonedDateTime now = ZonedDateTime.now();
//            if (!dynamicEntity.containsKey(DynamicEntityColumnName.CREATE_TIME.getColumnName())) {
//                dynamicEntity.put(DynamicEntityColumnName.CREATE_TIME.getColumnName(), now);
//            }
//            if (!dynamicEntity.containsKey(DynamicEntityColumnName.LM_TIME.getColumnName())) {
//                dynamicEntity.put(DynamicEntityColumnName.LM_TIME.getColumnName(), now);
//            }
//        }
//    }

}
