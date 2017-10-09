package com.pk.love.service;

import java.util.Set;

import com.pk.love.command.RecipeCommand;
import com.pk.love.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();
	Recipe findById(Long id);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
}
