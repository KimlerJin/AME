package com.ame.artemis;

import com.ame.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageService {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public MessageService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    //p2p模型
    public void produce(String message) {
        this.jmsTemplate.setPubSubDomain(false);
        this.jmsTemplate.convertAndSend("myQueue", message);
    }

    //pub-sub模型
    public void publish(TagEntity message, String destinationName) {

        this.jmsTemplate.convertAndSend(destinationName, message);
    }

}
