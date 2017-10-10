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
import com.pk.love.converters.IngredientToIngredientCommand;
import com.pk.love.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.pk.love.domain.Ingredient;
import com.pk.love.domain.Recipe;
import com.pk.love.repository.RecipeRepository;
import com.pk.love.service.IngredientService;

public class IngredientServiceImplTest {

	private final IngredientToIngredientCommand ingredientToCommand;
	
	@Mock
	RecipeRepository recipeRepository;
	
	IngredientService ingredientService;

	//init converters
	public IngredientServiceImplTest() {
		this.ingredientToCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(ingredientToCommand, recipeRepository);
	}

	@Test
	public void testFindByRecipeIdAndIngredientId() {
		
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

}
