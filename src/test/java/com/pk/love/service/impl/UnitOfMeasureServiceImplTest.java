package com.pk.love.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pk.love.command.UnitOfMeasureCommand;
import com.pk.love.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.pk.love.domain.UnitOfMeasure;
import com.pk.love.repository.UnitOfMeasureRepository;
import com.pk.love.service.UnitOfMeasureService;

public class UnitOfMeasureServiceImplTest {

	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	
	UnitOfMeasureService service;
	@Mock
	UnitOfMeasureRepository repository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new UnitOfMeasureServiceImpl(repository, unitOfMeasureToCommand);
	}

	@Test
	public void testListAllUoms() {
		//given
		Set<UnitOfMeasure> unitOfMeasure = new HashSet<>();
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		
		unitOfMeasure.add(uom1);
		unitOfMeasure.add(uom2);
		
		//when
		when(repository.findAll()).thenReturn(unitOfMeasure);
		
		Set<UnitOfMeasureCommand> commands = service.listAllUoms();
		
		//then
		assertEquals(2, commands.size());
		verify(repository, times(1)).findAll();
	}

}
