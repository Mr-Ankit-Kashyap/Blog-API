package com.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entities.Category;

public interface CategoryRepository extends JpaRepository<Category , Long> {
	
	

}
