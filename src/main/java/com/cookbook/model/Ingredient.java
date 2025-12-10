package com.cookbook.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Ingredient entity mapped to ingredients table.
 */
public class Ingredient implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer ingredientId;
    private String name;

    public Ingredient() {}

    public Ingredient(Integer ingredientId, String name) {
        this.ingredientId = ingredientId;
        this.name = name;
    }

    public Ingredient(String name) {
        this(null, name);
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;
        return Objects.equals(ingredientId, that.ingredientId) &&
               Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, name);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
               "ingredientId=" + ingredientId +
               ", name='" + name + '\'' +
               '}';
    }
}
