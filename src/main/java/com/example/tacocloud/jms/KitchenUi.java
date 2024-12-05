
package com.example.tacocloud.jms;

import org.springframework.stereotype.Component;

@Component
public class KitchenUi {
    public void displayOrder(Object order) {
        System.out.println("RECEIVED ORDER: " + order);
    }
}

