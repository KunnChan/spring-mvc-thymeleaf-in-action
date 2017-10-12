package com.pk.love.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pk.love.command.RecipeCommand;
import com.pk.love.service.ImageService;
import com.pk.love.service.RecipeService;

@Controller
public class ImageController {

	private final ImageService imageService;
	private final RecipeService recipeService;
	
	public ImageController(ImageService imageService, RecipeService recipeService) {
		super();
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	@GetMapping("recipe/{id}/image")
	public String showUploaderForm(@PathVariable Long id, Model model){
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/imageUploadForm";
	}
	
	@PostMapping("recipe/{id}/image")
	public String handleImagePost(@PathVariable Long id, 
						@RequestParam("file") MultipartFile file){
		imageService.saveImageFile(id, file);
		return "redirect:/recipe/"+id+"/show";
	}
	
	@GetMapping("recipe/{id}/recipeimage")
	public void renderImageFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException{
		RecipeCommand command = recipeService.findCommandById(id);
		
		byte[] byteArray = new byte[command.getImage().length];
		int i = 0;
		for (byte b : command.getImage()) {
			byteArray[i++] = b; //auto unboxing
		}
		response.setContentType("image/jpeg");
		InputStream is = new ByteArrayInputStream(byteArray);
		IOUtils.copy(is, response.getOutputStream());
	}
	
}
