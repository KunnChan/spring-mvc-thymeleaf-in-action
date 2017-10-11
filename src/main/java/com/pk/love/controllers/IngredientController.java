package com.pk.love.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pk.love.command.IngredientCommand;
import com.pk.love.command.RecipeCommand;
import com.pk.love.command.UnitOfMeasureCommand;
import com.pk.love.service.IngredientService;
import com.pk.love.service.RecipeService;
import com.pk.love.service.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;
	
	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}
	
	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable Long recipeId, Model model){
		log.info("Getting ingredient list for id : "+ recipeId);
		
		model.addAttribute("recipe" , recipeService.findCommandById(recipeId));
		return "recipe/ingredient/list";
	}
	@GetMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable Long recipeId,
										@PathVariable Long id, Model model){
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
		return "recipe/ingredient/show";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/new")
	public String newRecipe(@PathVariable Long recipeId, Model model){
		//make sure we use a good id value
		RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
		//TODO raise exception if null
		
		
		//need to return back parent id for hidden form property
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipe(recipeId);
		model.addAttribute("ingredient", ingredientCommand);
		
		//init uom
		ingredientCommand.setMeasure(new UnitOfMeasureCommand());
		
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		return "recipe/ingredient/ingredientform";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable Long recipeId,
						@PathVariable Long id, Model model){
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command, @PathVariable Long recipeId){
			command.setRecipe(recipeId);
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		log.info("Saved recipe id : "+ savedCommand.getRecipe());
		log.info( "Saved ingredient id "+ savedCommand.getId() );
		
		return "redirect:/recipe/"+ savedCommand.getRecipe() + "/ingredient/"+ savedCommand.getId() +"/show";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteById(@PathVariable Long recipeId, @PathVariable Long id){
		
		log.info("Delete id : "+ id);
		ingredientService.deleteById(recipeId, id);
		return "redirect:/recipe/"+ recipeId +"/ingredients";
	}
}
