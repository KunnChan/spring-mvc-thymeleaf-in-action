package com.pk.love.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pk.love.converters.RecipeCommandToRecipe;
import com.pk.love.converters.RecipeToRecipeCommand;
import com.pk.love.domain.Recipe;
import com.pk.love.repository.RecipeRepository;

public class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	RecipeCommandToRecipe commandToRecipe;
	
	@Mock
	RecipeToRecipeCommand recipeToCommand;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, commandToRecipe, recipeToCommand);
	}
	
	@Test
	public void testGetRecipeById() throws Exception{
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(1L)).thenReturn(recipeOptional);
		
		Recipe recipeReutrned = recipeService.findById(1L);
		
		assertNotNull("Null recipe returned", recipeReutrned);
		
		verify(recipeRepository, times(1)).findById(1L);
		verify(recipeRepository, never()).findAll();
	}

	@Test
	public void testGetRecipes() {
		Recipe recipe = new Recipe();
		Set<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);
		
		when(recipeService.getRecipes()).thenReturn(recipeData);
		
		Set<Recipe> recipes = recipeService.getRecipes();
		
		assertEquals(recipes.size(), 1);
		
		verify(recipeRepository, times(1)).findAll();
		
		
	}

}
