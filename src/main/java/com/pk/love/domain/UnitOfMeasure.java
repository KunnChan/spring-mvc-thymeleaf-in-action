package com.pk.love.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"ingredient"})
@Entity
public class UnitOfMeasure {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	
	@OneToOne( cascade = CascadeType.ALL)
	private Ingredient ingredient;

}
