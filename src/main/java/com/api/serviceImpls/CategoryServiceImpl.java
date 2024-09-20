package com.api.serviceImpls;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.api.dtos.CategoryDto;
import com.api.entities.Category;
import com.api.exceptions.ResourceNotFoundException;
import com.api.repositories.CategoryRepository;
import com.api.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;

	private ModelMapper modelMapper;

	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
//		convert categoryDto To category
		Category category = this.modelMapper.map(categoryDto, Category.class);

//		save category in database
		Category saveCategory = this.categoryRepository.save(category);

//		convert category To categoryDto
		CategoryDto saveCategoryDto = this.modelMapper.map(saveCategory, CategoryDto.class);

//		return categoryDto
		return saveCategoryDto;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
//		search category
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

//		set property in category
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());

//		save category
		Category updateCategory = this.categoryRepository.save(category);

//		convert category To categoryDto
		CategoryDto updateCategoryDto = this.modelMapper.map(updateCategory, CategoryDto.class);

//		return categoryDto
		return updateCategoryDto;
	}

	@Override
	public void deleteCategory(Long categoryId) {
//		search category
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
//		delete category
		this.categoryRepository.delete(category);

	}

	@Override
	public CategoryDto getCategoryById(Long categoryId) {
//		search category
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

//		convert category To categoryDto
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);

//		return categoryDto
		return categoryDto;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		return null;
	}

}
