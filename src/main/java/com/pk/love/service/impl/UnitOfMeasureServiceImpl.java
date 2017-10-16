package com.pk.love.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.pk.love.command.UnitOfMeasureCommand;
import com.pk.love.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.pk.love.repository.UnitOfMeasureRepository;
import com.pk.love.service.UnitOfMeasureService;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToCommand;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToCommand) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.unitOfMeasureToCommand = unitOfMeasureToCommand;
	}

	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		
		return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
				.map(unitOfMeasureToCommand::convert)
				.collect(Collectors.toSet());
	}

}
