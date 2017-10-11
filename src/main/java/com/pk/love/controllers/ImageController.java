package com.pk.love.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pk.love.service.ImageService;

@Controller
public class ImageController {

	private final ImageService imageService;

	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}
	
	@PostMapping("recipe/{id}/image")
	public String handleImagePost(@PathVariable Long id, 
						@RequestParam("file") MultipartFile file){
		imageService.saveImageFile(id, file);
		return "/recipe/"+id+"/show";
	}
	
}
