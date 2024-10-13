package com.example.tacocloud.Repositories;

import com.example.tacocloud.Security.User;
import com.example.tacocloud.tacos.Taco;
import com.example.tacocloud.tacos.TacoOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
    Page<Taco> findAll(Pageable pageable);
}
