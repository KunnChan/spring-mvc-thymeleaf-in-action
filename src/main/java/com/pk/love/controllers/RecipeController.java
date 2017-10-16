package com.pk.love.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.pk.love.command.RecipeCommand;
import com.pk.love.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	private final RecipeService recipeService;
	
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("/recipe/{id}/show")
	public String showById(@PathVariable Long id, Model model){
		model.addAttribute("recipe", recipeService.findById(id));
		return "recipe/show";
	}
	
	@GetMapping("recipe/new")
	public String newRecipe(Model model){
		model.addAttribute("recipe", new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
	}
	
	@GetMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable Long id, Model model){
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return RECIPE_RECIPEFORM_URL;
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindResult){
		if (bindResult.hasErrors()) {
			bindResult.getAllErrors().forEach( obj ->{
				log.debug(obj.toString());
			});
			return RECIPE_RECIPEFORM_URL;
		}
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipe/"+ savedCommand.getId()+"/show";
	}
	
	@GetMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable Long id){
		
		log.info("Delete id : "+ id);
		recipeService.deleteById(id);
		return "redirect:/";
	}
	
	/*@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public String handleNotFound(Exception ex, Model model){
		log.error("Not Found Error");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("404Error");
		mv.addObject("exception", ex);
		model.addAttribute("exception", ex);
		return "404Error";
	}
	*/
}
