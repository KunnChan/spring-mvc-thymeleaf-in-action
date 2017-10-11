package com.pk.love.service;

import com.pk.love.command.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);

	IngredientCommand saveIngredientCommand(IngredientCommand command);

	void deleteById(Long reipeId, Long id);

}
