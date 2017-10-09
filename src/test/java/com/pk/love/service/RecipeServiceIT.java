package com.pk.love.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pk.love.command.RecipeCommand;
import com.pk.love.converters.RecipeCommandToRecipe;
import com.pk.love.converters.RecipeToRecipeCommand;
import com.pk.love.domain.Recipe;
import com.pk.love.repository.RecipeRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

	public static final String NEW_DESCRIPTION = "New Description";
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RecipeCommandToRecipe commandToRecipe;
	
	@Autowired
	RecipeToRecipeCommand recipeToCommand;
	
	
	@Test
	@Transactional
	public void testSaveOfDescription() throws Exception{
		//given
		Iterable<Recipe> recipes = recipeRepository.findAll();
		Recipe testRecipe = recipes.iterator().next();
		RecipeCommand testRecipeCommand = recipeToCommand.convert(testRecipe);
		
		//when 
		testRecipeCommand.setDescription(NEW_DESCRIPTION);
		RecipeCommand saveDescription = recipeService.saveRecipeCommand(testRecipeCommand);
		
		//then
		assertEquals(NEW_DESCRIPTION, saveDescription.getDescription());
		assertEquals(testRecipe.getId(), saveDescription.getId());
		assertEquals(testRecipe.getCategories().size(), saveDescription.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(), saveDescription.getIngredients().size());
	}

}
