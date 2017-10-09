package com.pk.love.repository;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pk.love.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryTest {
	
	@Autowired
	UnitOfMeasureRepository repository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindByDescription() {
		Optional<UnitOfMeasure> result = repository.findByDescription("TeaSpoon");
		assertEquals("TeaSpoon", result.get().getDescription());
	}

	@Test
	public void testFindByDescriptionCup() {
		Optional<UnitOfMeasure> result = repository.findByDescription("Cup");
		assertEquals("Cup", result.get().getDescription());
	}
}
