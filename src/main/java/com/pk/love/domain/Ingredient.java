package com.pk.love.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String description;
	private BigDecimal amount;
	
	@ManyToOne
	private Recipe recipe;
	
	// in this case fetchType is optional bcz it default but look pretty
	@OneToOne(fetch = FetchType.EAGER)  
	private UnitOfMeasure measure;
	
	

	public Ingredient(String description, BigDecimal amount, UnitOfMeasure measure) {
		super();
		this.description = description;
		this.amount = amount;
		this.measure = measure;
	}
	public Ingredient() {
	}
	
}
