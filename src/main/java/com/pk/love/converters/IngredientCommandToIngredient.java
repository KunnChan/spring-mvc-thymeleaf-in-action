package com.pk.love.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.pk.love.command.IngredientCommand;
import com.pk.love.domain.Ingredient;

import lombok.Synchronized;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient>{

	private final UnitOfMeasureCommandToUnitOfMeasure uomCommandToUom;
	
	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomCommandToUom) {
		this.uomCommandToUom = uomCommandToUom;
	}

	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null) {
			return null;
		}
		final Ingredient ingre = new Ingredient();
		ingre.setId(source.getId());
		ingre.setDescription(source.getDescription());
		ingre.setAmount(source.getAmount());
		ingre.setMeasure(uomCommandToUom.convert(source.getMeasure()));
		return ingre;
	}

}
