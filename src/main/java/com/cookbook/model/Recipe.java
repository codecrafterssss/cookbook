package com.cookbook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Recipe entity representing recipes table.
 * Holds a list of RecipeIngredient for the relation.
 */
public class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer recipeId;
    private String title;
    private String instructions;
    private Integer cookTime; // minutes
    private Integer totalCalories;
    private Integer chefId; // foreign key
    private Chef chef; // optional convenience reference
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    public Recipe() {}

    public Recipe(Integer recipeId, String title, String instructions, Integer cookTime, Integer totalCalories, Integer chefId) {
        this.recipeId = recipeId;
        this.title = title;
        this.instructions = instructions;
        this.cookTime = cookTime;
        this.totalCalories = totalCalories;
        this.chefId = chefId;
    }

    public Recipe(String title, String instructions, Integer cookTime, Integer totalCalories, Integer chefId) {
        this(null, title, instructions, cookTime, totalCalories, chefId);
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Integer totalCalories) {
        this.totalCalories = totalCalories;
    }

    public Integer getChefId() {
        return chefId;
    }

    public void setChefId(Integer chefId) {
        this.chefId = chefId;
    }

    public Chef getChef() {
        return chef;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
        if (chef != null) this.chefId = chef.getChefId();
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients != null ? recipeIngredients : new ArrayList<>();
    }

    public void addRecipeIngredient(RecipeIngredient ri) {
        if (ri != null) {
            recipeIngredients.add(ri);
            ri.setRecipe(this);
            if (this.recipeId != null) ri.setRecipeId(this.recipeId);
        }
    }

    public void removeRecipeIngredient(RecipeIngredient ri) {
        if (ri != null) {
            recipeIngredients.remove(ri);
            ri.setRecipe(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;
        return Objects.equals(recipeId, recipe.recipeId) &&
               Objects.equals(title, recipe.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, title);
    }

    @Override
    public String toString() {
        return "Recipe{" +
               "recipeId=" + recipeId +
               ", title='" + title + '\'' +
               ", cookTime=" + cookTime +
               ", totalCalories=" + totalCalories +
               ", chefId=" + chefId +
               ", ingredientsCount=" + (recipeIngredients != null ? recipeIngredients.size() : 0) +
               '}';
    }
}
