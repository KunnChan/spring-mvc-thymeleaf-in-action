package com.pk.love.repository;

import org.springframework.data.repository.CrudRepository;

import com.pk.love.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{

}
