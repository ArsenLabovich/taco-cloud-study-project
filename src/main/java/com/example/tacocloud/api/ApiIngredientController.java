package com.example.tacocloud.api;

import com.example.tacocloud.Repositories.IngredientRepository;
import com.example.tacocloud.jms.OrderMessagingService;
import com.example.tacocloud.tacos.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class ApiIngredientController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public ApiIngredientController(IngredientRepository repo, OrderMessagingService messageService) {
        this.ingredientRepository = repo;
    }

    @GetMapping
    public Flux<Ingredient> allIngredients() {
        return ingredientRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Ingredient> saveIngredient(@RequestBody Ingredient ingredient) {
        return ingredientRepository.save(ingredient)
                .doOnNext(ingredient1 -> System.out.println("Ingredient saved: " + ingredient1));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteIngredient(@PathVariable("id") String ingredientId) {
        return ingredientRepository.deleteById(ingredientId)
                .doOnSuccess(aVoid -> System.out.println("Ingredient deleted: " + ingredientId));
    }

}