package com.pk.love.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pk.love.command.RecipeCommand;
import com.pk.love.converters.RecipeCommandToRecipe;
import com.pk.love.converters.RecipeToRecipeCommand;
import com.pk.love.domain.Recipe;
import com.pk.love.exceptions.NotFoundException;
import com.pk.love.repository.RecipeRepository;
import com.pk.love.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe commandToRecipe;
	private final RecipeToRecipeCommand recipeToCommand;
	
	
	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe commandToRecipe,
			RecipeToRecipeCommand recipeToCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.commandToRecipe = commandToRecipe;
		this.recipeToCommand = recipeToCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipes = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}

	@Override
	public Recipe findById(Long id) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		if(!recipeOptional.isPresent()){
			throw new NotFoundException("Recipe with id :  "+ id + " Not Found");
		}
		return recipeOptional.get();
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = commandToRecipe.convert(command);
		Recipe saveRecipe = recipeRepository.save(detachedRecipe);
		log.info("Save Recipe Successful with id : "+ saveRecipe.getId());
		return recipeToCommand.convert(saveRecipe);
	}

	@Override
	@Transactional
	public RecipeCommand findCommandById(Long id) {
		return recipeToCommand.convert(findById(id));
	}

	@Override
	public void deleteById(Long idToDelete) {
		recipeRepository.deleteById(idToDelete);
		
	}

}
