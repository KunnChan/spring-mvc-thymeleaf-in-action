package com.pk.love.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pk.love.command.NotesCommand;
import com.pk.love.domain.Notes;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>{

	@Override
	public NotesCommand convert(Notes source) {
		if(source == null)return null;
		
		final NotesCommand command = new NotesCommand();
			command.setId(source.getId());
			command.setRecipeNotes(source.getRecipeNotes());
		return command;
	}

}
