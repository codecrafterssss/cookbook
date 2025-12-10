package com.cookbook.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Bridge entity between Recipe and Ingredient.
 * DB composite PK is (recipe_id, ingredient_id).
 * Although your DB currently doesn't store quantity, this class can be extended later to store quantity/unit.
 */
public class RecipeIngredient implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer recipeId;
    private Integer ingredientId;

    /**
     * Optional convenience references (not persisted automatically).
     * Use in DAO mapping if you want full object graph.
     */
    private Recipe recipe;
    private Ingredient ingredient;

    public RecipeIngredient() {}

    public RecipeIngredient(Integer recipeId, Integer ingredientId) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
    }

    public RecipeIngredient(Integer recipeId, Integer ingredientId, Ingredient ingredient) {
        this(recipeId, ingredientId);
        this.ingredient = ingredient;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeIngredient that = (RecipeIngredient) o;
        return Objects.equals(recipeId, that.recipeId) &&
               Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, ingredientId);
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" +
               "recipeId=" + recipeId +
               ", ingredientId=" + ingredientId +
               ", ingredient=" + ingredient +
               '}';
    }
}
