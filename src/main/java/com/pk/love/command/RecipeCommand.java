package com.pk.love.command;

import java.util.HashSet;
import java.util.Set;

import com.pk.love.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand {

	private Long id;
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private String directions;
	private Byte[] image;
	
	private NotesCommand notes;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private Set<CategoryCommand> categories = new HashSet<>();
	private Difficulty difficulty;
}
