package com.codegnan.model;


public class Recipe {
    private int recipeId;
    private String title;
    private String instructions;
    private int cookTime;
    private int totalCalories;
    private int chefId;

    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public int getCookTime() { return cookTime; }
    public void setCookTime(int cookTime) { this.cookTime = cookTime; }

    public int getTotalCalories() { return totalCalories; }
    public void setTotalCalories(int totalCalories) { this.totalCalories = totalCalories; }

    public int getChefId() { return chefId; }
    public void setChefId(int chefId) { this.chefId = chefId; }
}
