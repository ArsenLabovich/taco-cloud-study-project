package com.example.tacocloud.tacos;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.Id;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class Ingredient {

    @Id
    private  String id;

    private  String name;

    private  Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
