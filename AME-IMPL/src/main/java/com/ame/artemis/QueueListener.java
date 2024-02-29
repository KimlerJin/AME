package com.ame.artemis;

import com.ame.core.RequestInfo;
import com.ame.entity.TagEntity;
import com.ame.service.ITagService;
import com.ame.util.JsonUtils;
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

@Component
public class QueueListener {

    @Autowired
    private ITagService tagService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MessageService messageService;


    private int i;

    private int j;

    @JmsListener(destination = "save_topic")
    public void processSaveMessage(TagEntity message) {
        tagService.save(message);
    }


    @JmsListener(destination = "internal/v1/gateway/telemetry/西门子测试设备")
    public void telemetry(Message message) throws InterruptedException, JMSException {
        String messageStr = "";
        if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) message;
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = bytesMessage.readBytes(b)) != -1) {
                messageStr = messageStr + new String(b, 0, len);
            }
        }
        if (message instanceof TextMessage) {
            messageStr = messageStr + ((TextMessage) message).getText();
        }
        ReceiveObject receiveObject = JsonUtils.jsonToObject(messageStr, ReceiveObject.class);
        long ts = receiveObject.getTs();
        ZonedDateTime zdt = Instant.ofEpochMilli(ts).atZone(ZoneId.systemDefault());
        RequestInfo.current().setRequestZonedDateTime(zdt);
        Map<String, Object> values = receiveObject.getValues();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String key = entry.getKey();
            TagEntity tagEntity = new TagEntity();
            tagEntity.setTagAddress(key);
            tagEntity.setTagName(key);
            tagEntity.setTagValue(entry.getValue().toString());
            if (redisTemplate.opsForValue().get(key) != null) {
                Object o = redisTemplate.opsForValue().get(key);
                if (!o.equals(entry.getValue())) {
                    setValueInRedis(tagEntity);
                } else {
                    System.out.println("do not thing");
                }
            } else {
                setValueInRedis(tagEntity);
            }
        }
    }

    @JmsListener(destination = "test_topic")
    public void processMessage(Message message) throws InterruptedException, JMSException {
        String messageStr = "";
        if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) message;
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = bytesMessage.readBytes(b)) != -1) {
                messageStr = messageStr + new String(b, 0, len);
            }
        }
        if (message instanceof TextMessage) {
            messageStr = messageStr + ((TextMessage) message).getText();
        }
        String[] values = messageStr.split(",");
        String tagAddress = values[0];
        String tagValue = values[1];
        TagEntity tagEntity = new TagEntity();
        tagEntity.setTagAddress(tagAddress);
        tagEntity.setTagName(tagAddress);
        tagEntity.setTagValue(tagValue);
        if (redisTemplate.opsForValue().get(tagAddress) != null) {
            Object o = redisTemplate.opsForValue().get(tagAddress);
            if (!o.equals(tagValue)) {
                setValueInRedis(tagEntity);
            } else {
                System.out.println("do not thing");
            }
        } else {
            setValueInRedis(tagEntity);
        }
    }

    private void setValueInRedis(TagEntity tagEntity) {
        messageService.publish(tagEntity, "save_topic");
        redisTemplate.opsForValue().set(tagEntity.getTagAddress(), tagEntity.getTagValue());

    }

}
