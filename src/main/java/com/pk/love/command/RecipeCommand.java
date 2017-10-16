package com.pk.love.command;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.pk.love.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand {

	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	
	@Min(1)
	@Max(999)
	private Integer prepTime;
	
	@Min(1)
	@Max(999)
	private Integer cookTime;
	
	@Min(1)
	@Max(999)
	private Integer servings;
	private String source;
	
	@URL
	private String url;
	
	@NotBlank
	private String directions;
	private Byte[] image;
	
	private NotesCommand notes;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private Set<CategoryCommand> categories = new HashSet<>();
	private Difficulty difficulty;
}
