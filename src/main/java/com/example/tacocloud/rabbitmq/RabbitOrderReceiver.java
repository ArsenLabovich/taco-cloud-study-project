package com.example.tacocloud.rabbitmq;

import com.example.tacocloud.tacos.TacoOrder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class RabbitOrderReceiver {
    private final RabbitTemplate rabbit;
    private final MessageConverter converter;
    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
        this.converter = rabbit.getMessageConverter();
    }
    public TacoOrder receiveOrder() {
        Message message = rabbit.receive("tacocloud.order");
        System.out.println("Order received from RabbitMQ: " + message);
        return message != null
                ? (TacoOrder) converter.fromMessage(message)
                : null;
    }
}