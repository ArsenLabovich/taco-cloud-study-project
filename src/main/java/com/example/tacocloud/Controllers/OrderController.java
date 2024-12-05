package com.example.tacocloud.Controllers;

import com.example.tacocloud.Properties.OrderProperties;
import com.example.tacocloud.Repositories.UserRepository;
import com.example.tacocloud.Security.User;
import com.example.tacocloud.tacos.TacoOrder;
import com.example.tacocloud.Repositories.OrderRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@Component
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderProperties orderProperties;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository, OrderProperties orderProperties) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderProperties = orderProperties;
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder tacoOrder() {
        return new TacoOrder();
    }

    @GetMapping("/current")
    public String orderForm(@ModelAttribute TacoOrder tacoOrder) {
        return "orderform";
    }

    @PostMapping("/current")
    public Mono<String> processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return Mono.just("orderform");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Mono<User> attachedUserMono;
        if (authentication.getPrincipal() instanceof User currentUser) {
            attachedUserMono = userRepository.findById(currentUser.getId());
        } else {
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            attachedUserMono = userRepository.findByUsername(oAuth2User.getName());
        }

        attachedUserMono.flatMap(attachedUser -> {order.setUser(attachedUser); return null;});
        if(order.getId() == null){
            for(Long i = 0L; i < Long.MAX_VALUE; i++){
                if(orderRepository.findById(String.valueOf(i)).block() == null){
                    order.setId(String.valueOf(i));
                    break;
                }
            }
        }
        orderRepository.save(order);
        sessionStatus.setComplete();
        return Mono.just("redirect:/orders/myorders");
/*                .doOnNext(o -> log.info("Order submitted: {}", o))
                .then(Mono.just("redirect:/orders/myorders"))
                .doOnTerminate(sessionStatus::setComplete)
                .subscribe();*/
        /*return attachedUserMono.flatMap(attachedUser -> {
                    order.setUser(attachedUser);
                    return orderRepository.save(order)
                            .doOnNext(o -> log.info("Order submitted: {}", o))
                            .then(Mono.just("redirect:/orders/myorders"));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));*/
    }


    @GetMapping("/myorders")
    public String ordersForUser(
            @AuthenticationPrincipal User user, Model model) {
        List<TacoOrder> list = new ArrayList<>();
        orderRepository.findByUserId(user.getId()).subscribe(list::add);
        model.addAttribute("orders",list);
        return "orderList";
    }
}
