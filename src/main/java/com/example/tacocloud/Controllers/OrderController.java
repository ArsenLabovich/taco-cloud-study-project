package com.example.tacocloud.Controllers;

import com.example.tacocloud.Properties.OrderProperties;
import com.example.tacocloud.Repositories.UserRepository;
import com.example.tacocloud.Security.User;
import com.example.tacocloud.tacos.TacoOrder;
import com.example.tacocloud.Repositories.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

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

    @GetMapping("/current")
    public String orderForm() {
        return "orderform";
    }

    @Transactional
    @PostMapping("/current")
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if(errors.hasErrors()){
            return "orderform";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User attachedUser;
        if(authentication.getPrincipal().getClass()==User.class){
            User currentUser = (User) authentication.getPrincipal();
            attachedUser = userRepository.findById(currentUser.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        }else{
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            attachedUser = userRepository.findByUsername(oAuth2User.getName());
        }
        order.setUser(attachedUser);
        orderRepository.save(order);
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        return "redirect:/orders/myorders";
    }

    @Transactional
    @GetMapping("/myorders")
    public String ordersForUser(
            @AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, orderProperties.getPageSize());
        model.addAttribute("orders",
                orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }
}
