package com.example.tacocloud.Repositories;


import com.example.tacocloud.tacos.Taco;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepository extends ReactiveMongoRepository<Taco, String> {
}
