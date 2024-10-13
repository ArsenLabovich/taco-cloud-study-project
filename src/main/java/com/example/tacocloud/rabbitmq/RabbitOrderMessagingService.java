package com.example.tacocloud.rabbitmq;

import com.example.tacocloud.jms.OrderMessagingService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tacocloud.tacos.TacoOrder;


@Service
public class RabbitOrderMessagingService implements OrderMessagingService {
    private final RabbitTemplate rabbit;
    @Autowired
    public RabbitOrderMessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }
    public void sendOrder(TacoOrder order) {
        System.out.println("Order sent to RabbitMQ: " + order);
        rabbit.convertAndSend("tacocloud.order", "orderRoutingKey", order);
    }
}