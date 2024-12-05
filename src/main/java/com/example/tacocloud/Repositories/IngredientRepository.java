package com.example.tacocloud.Repositories;

import com.example.tacocloud.tacos.Ingredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IngredientRepository extends ReactiveMongoRepository<Ingredient, String> {
    Mono<Ingredient> findBySlug(String slug);

}
