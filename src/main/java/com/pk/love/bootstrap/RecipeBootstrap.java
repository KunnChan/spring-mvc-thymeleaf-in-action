package com.pk.love.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pk.love.domain.Category;
import com.pk.love.domain.Difficulty;
import com.pk.love.domain.Ingredient;
import com.pk.love.domain.Notes;
import com.pk.love.domain.Recipe;
import com.pk.love.domain.UnitOfMeasure;
import com.pk.love.repository.CategoryRepository;
import com.pk.love.repository.RecipeRepository;
import com.pk.love.repository.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent>{

	private final CategoryRepository categoryRepository;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		recipeRepository.saveAll(getRecipe());
	}
	
	private List<Recipe> getRecipe(){
		
		List<Recipe> recipes = new ArrayList<>();
		
		log.debug("amazing lombok");
		
		Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");
		if(!eachUomOptional.isPresent()) throw new RuntimeException("Each UOM Not Found!");
		
		Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("TeaSpoon");
		if(!teaSpoonUomOptional.isPresent()) throw new RuntimeException("TeaSpoon UOM Not Found!");
		
		Optional<UnitOfMeasure> tablesSpoonUomOptional = unitOfMeasureRepository.findByDescription("TablesSpoon");
		if(!tablesSpoonUomOptional.isPresent()) throw new RuntimeException("TableSpoon UOM Not Found!");
		
		Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("Cup");
		if(!cupUomOptional.isPresent()) throw new RuntimeException("Cup UOM Not Found!");
		
		Optional<UnitOfMeasure> pinchUomOptional = unitOfMeasureRepository.findByDescription("Pinch");
		if(!pinchUomOptional.isPresent()) throw new RuntimeException("Pinch UOM Not Found!");
		
		Optional<UnitOfMeasure> ounceUomOptional = unitOfMeasureRepository.findByDescription("Ounce");
		if(!ounceUomOptional.isPresent()) throw new RuntimeException("Ounce UOM Not Found!");
		
		Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");
		if(!dashUomOptional.isPresent()) throw new RuntimeException("Dash UOM Not Found!");
		
		Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");
		if(!pintUomOptional.isPresent()) throw new RuntimeException("Pint UOM Not Found!");
		
		UnitOfMeasure eachUom = eachUomOptional.get();
		UnitOfMeasure tableSpoonUom = tablesSpoonUomOptional.get();
		UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
		UnitOfMeasure cupUom = cupUomOptional.get();
		UnitOfMeasure pinchUom = pinchUomOptional.get();
		UnitOfMeasure ounceUom = ounceUomOptional.get();
		UnitOfMeasure dashUom = dashUomOptional.get();
		UnitOfMeasure pintUom = pintUomOptional.get();
		
		Optional<Category> americanCategory = categoryRepository.findByDescription("Amarican");
		if(!americanCategory.isPresent()) throw new RuntimeException("American Category not found! ");
		
		Optional<Category> italianCategory = categoryRepository.findByDescription("Italian");
		if(!italianCategory.isPresent()) throw new RuntimeException("Italian Category not found! ");
		
		Optional<Category> maxicanCategory = categoryRepository.findByDescription("Maxican");
		if(!maxicanCategory.isPresent()) throw new RuntimeException("Maxican Category not found! ");
		
		Optional<Category> fastFoodCategory = categoryRepository.findByDescription("Fast Food");
		if(!fastFoodCategory.isPresent()) throw new RuntimeException("Fast Food Category not found! ");
		
		Category ameCategory = americanCategory.get();
		Category itaCategory = italianCategory.get();
		Category maxCategory = maxicanCategory.get();
		Category fastCategory = fastFoodCategory.get();
		
		//Yummy Guac
		Recipe guacRecipe = new Recipe();
		guacRecipe.setDescription("Spicy Grilled Chicken Tacos");
		guacRecipe.setPrepTime(10);
		guacRecipe.setCookTime(25);
		guacRecipe.setDifficulty(Difficulty.MODERATE);
		guacRecipe.setDirections("Guacamole, a dip made from avocados, is originally from Mexico. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity to balance the richness of the avocado. Then comes chopped cilantro, chiles, onion, and tomato, if you want.The trick to making perfect guacamole is using good, ripe avocados. Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using.");
		
		Notes notes = new Notes();
		//notes.setRecipe(guacRecipe); //I had defined it in setter method
		notes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.");
		
		guacRecipe.setNotes(notes);
		
		guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
		guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(1), teaSpoonUom));
		guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(1), tableSpoonUom));
		guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), cupUom));
		guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));
		guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(1), eachUom));
		
		guacRecipe.getCategories().add(ameCategory);
		guacRecipe.getCategories().add(maxCategory);
		
		guacRecipe.setServings(4);
		guacRecipe.setSource("Simply Recipe");
		guacRecipe.setUrl("http://www.simplyrecipe.com/gauc");
		
		recipes.add(guacRecipe);
		
		//Spicy Grill
		Recipe girllRecipe = new Recipe();
		girllRecipe.setDescription("Perfect Guacamole");
		girllRecipe.setPrepTime(10);
		girllRecipe.setCookTime(5);
		girllRecipe.setDifficulty(Difficulty.EASY);
		girllRecipe.setDirections("Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!");
		
		Notes notes1 = new Notes();
	//	notes1.setRecipe(girllRecipe); //I had defined it in setter method
		notes1.setRecipeNotes("The ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online.I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.");
		
		girllRecipe.setNotes(notes1);
		girllRecipe.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tableSpoonUom));
		girllRecipe.addIngredient(new Ingredient("dried oregano", new BigDecimal(2), teaSpoonUom));
		girllRecipe.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), teaSpoonUom));
		girllRecipe.addIngredient(new Ingredient("baby arugula", new BigDecimal(3), ounceUom));
		girllRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), cupUom));
		girllRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));
		girllRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(1), eachUom));
		
		girllRecipe.getCategories().add(fastCategory);
		girllRecipe.getCategories().add(itaCategory);
		
		girllRecipe.setServings(3);
		girllRecipe.setSource("Simply Recipe");
		girllRecipe.setUrl("http://www.simplyrecipe.com/grill");
		
		recipes.add(girllRecipe);
		
		return recipes ;
		
	}

	
	 
}
