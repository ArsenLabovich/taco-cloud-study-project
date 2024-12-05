package com.example.tacocloud.tacos;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Document(collection = "ingredients")
public class Ingredient {

    @Id
    private String id;

    @Indexed(unique = true)
    private @NonNull String slug;

    @Indexed(unique = true)
    private @NonNull String name;

    @Indexed(unique = true)
    private @NonNull Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }


}