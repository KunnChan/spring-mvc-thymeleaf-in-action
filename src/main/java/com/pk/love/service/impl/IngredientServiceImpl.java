package com.pk.love.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pk.love.command.IngredientCommand;
import com.pk.love.converters.IngredientCommandToIngredient;
import com.pk.love.converters.IngredientToIngredientCommand;
import com.pk.love.domain.Ingredient;
import com.pk.love.domain.Recipe;
import com.pk.love.repository.RecipeRepository;
import com.pk.love.repository.UnitOfMeasureRepository;
import com.pk.love.service.IngredientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

	private final IngredientToIngredientCommand ingredientToCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		this.ingredientToCommand = ingredientToCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
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

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipe());
		
		if (!recipeOptional.isPresent()) {
			log.error("Recipe not found for id : "+ command.getRecipe());
			return new IngredientCommand();
		}else{
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId()))
					.findFirst();
			if (ingredientOptional.isPresent()) {
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());
				ingredientFound.setMeasure(unitOfMeasureRepository
						.findById(command.getMeasure().getId())
						.orElseThrow(()-> new RuntimeException("Uom NOT FOUND")));
				//TODO address this
			}else{
				//ad new Ingredient
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}
			Recipe savedRecipe = recipeRepository.save(recipe);
			
			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
					.findFirst();
			//check by description
			if (!savedIngredientOptional.isPresent()) {
				//not totally safe... but best guess
				
				savedRecipe.getIngredients().forEach(System.out::println);
				
				savedIngredientOptional = savedRecipe.getIngredients().stream()
						.filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
						.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
						.filter(recipeIngredients -> recipeIngredients.getMeasure().getId().equals(command.getMeasure().getId()))
						.findFirst();
			}
			//todo check for fail
			return ingredientToCommand.convert(savedIngredientOptional.get());
		}
		
	}

	@Override
	public void deleteById(Long recipeId, Long id) {
		log.info("Deleting ingredient : "+ recipeId + " id: "+ id);
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if (recipeOptional.isPresent()) {
			log.info("Found Recipe witt");
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe
					.getIngredients()
					.stream()
					.filter(ing -> ing.getId().equals(id))
					.findFirst();
			
			if (ingredientOptional.isPresent()) {
				log.debug("Found Ingredient");
				Ingredient ingredientToDelete = ingredientOptional.get();
				ingredientToDelete.setRecipe(null);
				recipe.getIngredients().remove(ingredientOptional.get());
				recipeRepository.save(recipe);
			}
			
		}else {
			log.debug("Recipe id Not found for id : "+ recipeId);
		}
	}

}
