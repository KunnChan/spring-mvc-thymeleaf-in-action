package com.pk.love.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.pk.love.command.IngredientCommand;
import com.pk.love.domain.Ingredient;

import lombok.Synchronized;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand>{

	private final UnitOfMeasureToUnitOfMeasureCommand converter;
	
	
	public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand converter) {
		this.converter = converter;
	}


	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient source) {
		if(source == null)return null;
		
		final IngredientCommand command = new IngredientCommand();
			command.setId(source.getId());
			command.setAmount(source.getAmount());
			command.setDescription(source.getDescription());
			command.setMeasure(converter.convert(source.getMeasure()));
			if(source.getRecipe() != null)
				command.setRecipe(source.getRecipe().getId());
		return command;
	}

}
