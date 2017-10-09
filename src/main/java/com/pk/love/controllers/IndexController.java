package com.pk.love.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pk.love.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {

	private final RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping({"","/","/index"})
	public String index(Model model){
		log.debug("Index log work");
		model.addAttribute("recipes", recipeService.getRecipes());
		return "index";
	}
}
