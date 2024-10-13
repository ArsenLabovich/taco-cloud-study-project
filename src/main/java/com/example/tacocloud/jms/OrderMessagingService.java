
package com.example.tacocloud.jms;

import com.example.tacocloud.tacos.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
}
