package com.example.tacocloud.rabbitmq;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.tacocloud.tacos.TacoOrder;
import com.example.tacocloud.jms.KitchenUi;

@Component
public class OrderListener {
    private KitchenUi ui;
    @Autowired
    public OrderListener(KitchenUi ui) {
        this.ui = ui;
    }
    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(TacoOrder order) {
        ui.displayOrder(order);
    }
}