package com.example.tacocloud.Repositories;

import com.example.tacocloud.tacos.TacoOrder;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<TacoOrder, String> {

    Flux<TacoOrder> findByUserId(String userID);
}
