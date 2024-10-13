package com.example.tacocloud.api;
import com.example.tacocloud.Repositories.OrderRepository;
/*import com.example.tacocloud.jms.OrderMessagingService;*/
import com.example.tacocloud.jms.OrderMessagingService;
import com.example.tacocloud.tacos.TacoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path="/api/orders", produces="application/json")
public class ApiOrderController {

    private final OrderRepository orderRepository;
    private final OrderMessagingService messageService;



    @Autowired
    public ApiOrderController(OrderRepository repo, OrderMessagingService messageService) {
        this.orderRepository = repo;
        this.messageService = messageService;
    }


    @GetMapping
    public Iterable<TacoOrder> allIngredients() {
        return orderRepository.findAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder saveIngredient(@RequestBody TacoOrder tacoOrder) {
        messageService.sendOrder(tacoOrder);
        return orderRepository.save(tacoOrder);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") Long orderId) {
        orderRepository.deleteById(orderId);
    }
}