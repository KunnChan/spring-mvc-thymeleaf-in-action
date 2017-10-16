package com.pk.love.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pk.love.command.RecipeCommand;
import com.pk.love.service.ImageService;
import com.pk.love.service.RecipeService;

public class ImageControllerTest {

	@Mock
	ImageService imageService;
	
	@Mock
	RecipeService recipeServie;
	
	ImageController imageController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageController = new ImageController(imageService, recipeServie);
		mockMvc = MockMvcBuilders.standaloneSetup(imageController)
				.setControllerAdvice(new ExceptionHandlerController())
				.build();
	}

	@Test
	public void testHandleImagePost() throws Exception{
		MockMultipartFile file = new MockMultipartFile("file", "testing.txt", "text/plain", "Spring Guru".getBytes());
		
		this.mockMvc.perform(multipart("/recipe/1/image").file(file))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/recipe/1/show"));
	}
	
	@Test
	public void testRenderImageFromDB() throws Exception{
		//given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		
		String s = "fake image text";
		Byte[] byteBoxed = new Byte[s.getBytes().length];
		
		int i= 0;
		for (byte b : s.getBytes()) {
			byteBoxed[i++] = b;
		}
		command.setImage(byteBoxed);
		
		when(recipeServie.findCommandById(anyLong())).thenReturn(command);
		
		//when
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
					.andExpect(status().isOk())
					.andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		
		assertEquals(s.getBytes().length, responseBytes.length);
	}
	
	@Test
	public void testGetImageNumberFormatException() throws Exception{
		
		mockMvc.perform(get("/recipe/sdd/recipeimage"))
			.andExpect(status().isBadRequest())
			.andExpect(view().name("400Error"));
	}

}
