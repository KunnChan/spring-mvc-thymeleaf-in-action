package com.pk.love.controllers;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.pk.love.domain.Recipe;
import com.pk.love.service.RecipeService;

public class IndexControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	IndexController controller;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new IndexController(recipeService);
	}
	@Test
	public void testMockMvc() throws Exception{
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
	}
	@Test
	public void testIndex() {
		//given
	    Set<Recipe> recipes = new HashSet<>();
	    Recipe r1 = new Recipe();
	    r1.setId(1L);
	    Recipe r2 = new Recipe();
	    r2.setId(2L);
	    recipes.add(r1);
	    recipes.add(r2);
	    
	    when(recipeService.getRecipes()).thenReturn(recipes);
	    
	    ArgumentCaptor<Set> argumentCaptor = ArgumentCaptor.forClass(Set.class);
	    //when
	    String viewName = controller.index(model);
	    
	    //then
		assertEquals("index", viewName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute("recipes", argumentCaptor.capture());
		
		Set<Recipe> result = argumentCaptor.getValue();
		
		assertEquals(1, result.size());
	}
	

}
