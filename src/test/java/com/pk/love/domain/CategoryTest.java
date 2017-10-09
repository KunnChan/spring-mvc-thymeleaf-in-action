package com.pk.love.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CategoryTest {

//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}

	Category category;
	@Before
	public void setUp() throws Exception {
		category = new Category();
	}
	
	@Test
	public void getId() throws Exception{
		Long idValue = 4L;
		category.setId(idValue +2);
		
		assertEquals(category.getId(), idValue);
	}
	
	

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

}
