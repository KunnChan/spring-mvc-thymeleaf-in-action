package com.pk.love.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.pk.love.domain.Recipe;
import com.pk.love.repository.RecipeRepository;
import com.pk.love.service.ImageService;

public class ImageServiceImplTest {
	
	@Mock
	RecipeRepository recipeRepository;
	
	ImageService imageService;
	

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	public void testSaveImageFile() throws Exception{
		//given
		Long id = 1L;
		MockMultipartFile file = new MockMultipartFile("file", "testing.txt", "text/plain", "Spring Guru".getBytes());
		
		Recipe recipe = new Recipe();
		recipe.setId(id);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		//when 
		imageService.saveImageFile(id, file);
		
		//then
		verify(recipeRepository, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(file.getBytes().length, savedRecipe.getImage().length);
	}

}
