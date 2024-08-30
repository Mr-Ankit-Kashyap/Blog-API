package com.api.services;

import java.util.List;

import com.api.dtos.CategoryDto;

public interface CategoryService {

	 CategoryDto createCategory(CategoryDto categoryDto);

	 CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

	 void deleteCategory(Long categoryId);

	 CategoryDto getCategoryById(Long categoryId);

	 List<CategoryDto> getAllCategory();	 	 
}