package com.pk.love.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pk.love.command.IngredientCommand;
import com.pk.love.converters.IngredientCommandToIngredient;
import com.pk.love.converters.IngredientToIngredientCommand;
import com.pk.love.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.pk.love.domain.Ingredient;
import com.pk.love.domain.Recipe;
import com.pk.love.repository.RecipeRepository;
import com.pk.love.repository.UnitOfMeasureRepository;
import com.pk.love.service.IngredientService;

public class IngredientServiceImplTest {

	IngredientToIngredientCommand ingredientToCommand;
	IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	IngredientService ingredientService;

	//init converters
	public IngredientServiceImplTest() {
		this.ingredientToCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(ingredientToCommand, ingredientCommandToIngredient, recipeRepository, unitOfMeasureRepository);
	}
	
	@Test
	public void testFindByRecipeIdAndHappyPath() throws Exception {
		
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ingre1 = new Ingredient();
		ingre1.setId(1L);
		Ingredient ingre2 = new Ingredient();
		ingre2.setId(2L);
		Ingredient ingre3 = new Ingredient();
		ingre3.setId(3L);
		
		recipe.addIngredient(ingre1);
		recipe.addIngredient(ingre2);
		recipe.addIngredient(ingre3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		//when
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//then
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
		
		
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipe());
		verify(recipeRepository, times(1)).findById(anyLong());
	}
	
	@Test
	public void testSaveRecipeCommand() throws Exception{
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(1L);
		command.setRecipe(2L);
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		//when
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		//then
		assertEquals(Long.valueOf(3L), savedCommand.getId());
		
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}
	@Test
	public void testDeleteBy() throws Exception{
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Ingredient ingredient = new Ingredient();
		ingredient.setId(1L);
		recipe.addIngredient(ingredient);
		ingredient.setRecipe(recipe);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//when
		ingredientService.deleteById(anyLong(),1L);
		// no 'when()', since method has void return type
		
		//then
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}

}
