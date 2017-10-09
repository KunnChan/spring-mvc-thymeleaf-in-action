package com.pk.love.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pk.love.command.RecipeCommand;
import com.pk.love.domain.Category;
import com.pk.love.domain.Difficulty;
import com.pk.love.domain.Ingredient;
import com.pk.love.domain.Notes;
import com.pk.love.domain.Recipe;

public class RecipeToRecipeCommandTest {

	public static final Long RECIPE_ID = 1L;
	public static final Integer COOK_TIME = Integer.valueOf("5");
	public static final Integer PREP_TIME = Integer.valueOf("7");
	public static final String DESCRIPTION = "My Recipe";
	public static final String DIRECTION = "Directions";
	public static final Difficulty DIFFICULTY = Difficulty.EASY;
	public static final Integer SERVINGS = Integer.valueOf("3");
	public static final String SOURCE = "Source";
	public static final String URL = "Some URL";
	
	public static final Long CAT_ID_1 = 1L;
	public static final Long CAT_ID_2 = 2L;
	public static final Long INGRED_ID_1 = 3L;
	public static final Long INGRED_ID_2 = 4L;
	public static final Long NOTES_ID = 9L;
	
	RecipeToRecipeCommand converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
					new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()), 
					new NotesToNotesCommand());
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Recipe()));
	}

	@Test
	public void testConvert() {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);
		recipe.setCookTime(COOK_TIME);
		recipe.setPrepTime(PREP_TIME);
		recipe.setDescription(DESCRIPTION);
		recipe.setDirections(DIRECTION);
		recipe.setDifficulty(DIFFICULTY);
		recipe.setServings(SERVINGS);
		recipe.setSource(SOURCE);
		recipe.setUrl(URL);
		
		Notes notes = new Notes();
		notes.setId(NOTES_ID);
		
		recipe.setNotes(notes);
		
		Category category = new Category();
		category.setId(CAT_ID_1);
		
		Category category2 = new Category();
		category2.setId(CAT_ID_2);
		
		recipe.getCategories().add(category);
		recipe.getCategories().add(category2);
		

		Ingredient ingre1 = new Ingredient();
		ingre1.setId(INGRED_ID_1);
		
		Ingredient ingre2 = new Ingredient();
		ingre2.setId(INGRED_ID_2);
		
		recipe.getIngredients().add(ingre1);
		recipe.getIngredients().add(ingre2);
		
		//When
		RecipeCommand result = converter.convert(recipe);
		
		assertNotNull(result);
		assertEquals(RECIPE_ID, result.getId());
	}

}
