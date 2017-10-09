package com.pk.love.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.pk.love.command.CategoryCommand;
import com.pk.love.domain.Category;

import lombok.Synchronized;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand>{

	@Synchronized
	@Nullable
	@Override
	public CategoryCommand convert(Category source) {
		
		if (source == null) {
			return null;
		}
		
		final CategoryCommand command = new CategoryCommand();
			command.setId(source.getId());
			command.setDescription(source.getDescription());
		return command;
		
	}

}
