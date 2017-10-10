package com.pk.love.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pk.love.command.IngredientCommand;
import com.pk.love.converters.IngredientToIngredientCommand;
import com.pk.love.domain.Recipe;
import com.pk.love.repository.RecipeRepository;
import com.pk.love.service.IngredientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

	private final IngredientToIngredientCommand ingredientToCommand;
	private final RecipeRepository recipeRepository;
	
	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToCommand, RecipeRepository recipeRepository) {
		this.ingredientToCommand = ingredientToCommand;
		this.recipeRepository = recipeRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if (!recipeOptional.isPresent()) {
			// todo impl error handling
			log.error("recipe id not found. Id : "+ recipeId);
		}
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(id))
					.map( ingredient -> ingredientToCommand.convert(ingredient))
					.findFirst();
		if (!ingredientCommandOptional.isPresent()) {
			// todo impl error handling
			log.error("Ingredient id not found with ingredientId : " + id);
		}
		return ingredientCommandOptional.get();
	}

}
