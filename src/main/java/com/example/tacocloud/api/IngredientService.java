package com.example.tacocloud.api;

import com.example.tacocloud.tacos.Ingredient;

public interface IngredientService
{
    Iterable<Ingredient> findAll();
    Ingredient addIngredient(Ingredient ingredient);
}
