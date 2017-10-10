package com.pk.love.controllers;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pk.love.command.RecipeCommand;
import com.pk.love.domain.Recipe;
import com.pk.love.service.RecipeService;

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;
	
	RecipeController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetRecipe() throws Exception{
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		when(recipeService.findById(1L)).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/1/show"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"))
			.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testGetNewRecipeForm() throws Exception{
		mockMvc.perform(get("/recipe/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipeform"))
			.andExpect(model().attributeExists("recipe"));
		
	}
	
	@Test
	public void testPostNewRecipeForm() throws Exception{
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		
		when(recipeService.saveRecipeCommand(command)).thenReturn(command);
		
		mockMvc.perform(post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some description"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/show/2"));
			
	}
	
	@Test
	public void testGetUpdateView() throws Exception{
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);
		
		when(recipeService.findCommandById(2L)).thenReturn(command);
		
		mockMvc.perform(get("/recipe/1/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipeform"))
			.andExpect(model().attributeExists("recipe"));
		
	}

}
