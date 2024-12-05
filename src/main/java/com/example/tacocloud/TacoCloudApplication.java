package com.example.tacocloud;

import com.example.tacocloud.Repositories.IngredientRepository;
import com.example.tacocloud.Repositories.OrderRepository;
import com.example.tacocloud.Repositories.TacoRepository;
import com.example.tacocloud.Repositories.UserRepository;
import com.example.tacocloud.tacos.Ingredient;
import com.example.tacocloud.tacos.Taco;
import com.example.tacocloud.tacos.TacoOrder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "com.example.tacocloud.Repositories")
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository,
                                        UserRepository userRepository,
                                        TacoRepository tacoRepository,
                                        OrderRepository orderRepository) {
        return args -> {
            Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
            Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP);
            Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
            Ingredient carnitas = new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN);
            Ingredient tomatoes = new Ingredient(        "TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES);
            Ingredient lettuce = new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES);
            Ingredient cheddar = new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE);
            Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE);
            Ingredient salsa = new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE);
            Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE);
            ingredientRepository.findBySlug(flourTortilla.getName())
                    .switchIfEmpty(ingredientRepository.save(flourTortilla)).block();
            ingredientRepository.findBySlug(cornTortilla.getName())
                    .switchIfEmpty(ingredientRepository.save(cornTortilla)).block();
            ingredientRepository.findBySlug(groundBeef.getName())
                    .switchIfEmpty(ingredientRepository.save(groundBeef)).block();
            ingredientRepository.findBySlug(carnitas.getName())
                    .switchIfEmpty(ingredientRepository.save(carnitas)).block();
            ingredientRepository.findBySlug(tomatoes.getName())
                    .switchIfEmpty(ingredientRepository.save(tomatoes)).block();
            ingredientRepository.findBySlug(lettuce.getName())
                    .switchIfEmpty(ingredientRepository.save(lettuce)).block();
            ingredientRepository.findBySlug(cheddar.getName())
                    .switchIfEmpty(ingredientRepository.save(cheddar)).block();
            ingredientRepository.findBySlug(jack.getName())
                    .switchIfEmpty(ingredientRepository.save(jack)).block();
            ingredientRepository.findBySlug(salsa.getName())
                    .switchIfEmpty(ingredientRepository.save(salsa)).block();
            ingredientRepository.findBySlug(sourCream.getName())
                    .switchIfEmpty(ingredientRepository.save(sourCream)).block();
        };
    }


}
