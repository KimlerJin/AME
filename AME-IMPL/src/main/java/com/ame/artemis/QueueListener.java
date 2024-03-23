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



    @JmsListener(destination = "devices.TEST001.data")
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
//        if (redisTemplate.opsForValue().get(tagAddress) != null) {
//            Object o = redisTemplate.opsForValue().get(tagAddress);
//            if (!o.equals(tagValue)) {
//                setValueInRedis(tagEntity);
//            } else {
//                System.out.println("do not thing");
//            }
//        } else {
//            setValueInRedis(tagEntity);
//        }
    }

    private void setValueInRedis(TagEntity tagEntity) {
        messageService.publish(tagEntity, "save_topic");
        redisTemplate.opsForValue().set(tagEntity.getTagAddress(), tagEntity.getTagValue());

    }

}
