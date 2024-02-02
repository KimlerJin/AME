package com.ame.hibernate;


import com.ame.core.RequestInfo;
import com.ame.entity.IBaseEntity;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.MergeContext;
import org.hibernate.event.spi.MergeEvent;
import org.hibernate.event.spi.MergeEventListener;

import java.time.ZonedDateTime;
import java.util.Map;

public class EntityPreMergedListener implements MergeEventListener {

    private static final long serialVersionUID = 5411225541188623910L;

    public static void setEntityProperties(IBaseEntity baseEntity) {
        baseEntity.setModifyBid(-1);
        baseEntity.setRowLogId("-1");

        RequestInfo current = RequestInfo.current();
        if (current != null) {
            baseEntity.setLastModifyUserId(current.getUserId());
            baseEntity.setLastModifyUserName(current.getUserName());
            baseEntity.setLastModifyUserFullName((current.getUserFirstName() == null ? "" : current.getUserFirstName())
                + (current.getUserLastName() == null ? "" : current.getUserLastName()));
            baseEntity.setLastModifyIp(current.getUserIpAddress());
            baseEntity.setLastModifyTime(current.getRequestZonedDateTime());
        } else {
            baseEntity.setLastModifyTime(ZonedDateTime.now());
        }

        // 判断current是否为空
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onMerge(MergeEvent event) throws HibernateException {
        Object entity = event.getOriginal();
        if (entity instanceof IBaseEntity) {
            setEntityProperties((IBaseEntity)entity);
        } else if (entity instanceof Map) {
//            preCustomObjectSavedOrMerged((Map<String, Object>)entity);
        }
    }

    @Override
    public void onMerge(MergeEvent mergeEvent, MergeContext mergeContext) throws HibernateException {
        Object entity = mergeEvent.getOriginal();
        if (entity instanceof IBaseEntity) {
            setEntityProperties((IBaseEntity)entity);
        } else if (entity instanceof Map) {
//            preCustomObjectSavedOrMerged((Map<String, Object>)entity);
        }
    }

//    @SuppressWarnings("rawtypes")

//    private void preCustomObjectSavedOrMerged(Map<String, Object> dynamicEntity) {
//        RequestInfo current = RequestInfo.current();
//        if (current != null) {
//            dynamicEntity.put(DynamicEntityColumnName.LM_USER_ID.getColumnName(), current.getUserId());
//            dynamicEntity.put(DynamicEntityColumnName.LM_USER_NAME.getColumnName(), current.getUserName());
//            dynamicEntity.put(DynamicEntityColumnName.LM_USER_FULL_NAME.getColumnName(),
//                (current.getUserFirstName() == null ? "" : current.getUserFirstName())
//                    + (current.getUserLastName() == null ? "" : current.getUserLastName()));
//            dynamicEntity.put(DynamicEntityColumnName.LM_IP.getColumnName(), current.getUserIpAddress());
//            dynamicEntity.put(DynamicEntityColumnName.LM_TIME.getColumnName(), current.getRequestZonedDateTime());
//        } else {
//            dynamicEntity.put(DynamicEntityColumnName.LM_TIME.getColumnName(), ZonedDateTime.now());
//        }
//
//    }
}
