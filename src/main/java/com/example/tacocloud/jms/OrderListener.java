/*
package com.example.tacocloud.jms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.example.tacocloud.tacos.TacoOrder;
@Profile("jms-listener")
@Component
public class OrderListener {

    private final KitchenUi ui;

    @Autowired
    public OrderListener(KitchenUi ui) {
        this.ui = ui;
    }
    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(TacoOrder order) {
        ui.displayOrder(order);
    }
}*/