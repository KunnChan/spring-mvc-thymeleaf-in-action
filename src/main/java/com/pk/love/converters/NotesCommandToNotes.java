package com.pk.love.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.pk.love.command.NotesCommand;
import com.pk.love.domain.Notes;

import lombok.Synchronized;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes>{

	@Synchronized
	@Nullable
	@Override
	public Notes convert(NotesCommand source) {
		if( source == null)	return null;
		
		final Notes note = new Notes();
		note.setId(source.getId());
		note.setRecipeNotes(source.getRecipeNotes());
		return note;
	}

}
