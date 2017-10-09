package com.pk.love.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pk.love.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{

	Optional<Category> findByDescription(String description);
}
