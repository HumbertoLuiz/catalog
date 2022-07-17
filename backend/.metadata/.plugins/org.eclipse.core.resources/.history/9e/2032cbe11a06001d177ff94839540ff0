package io.github.humbertoluiz.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.humbertoluiz.catalog.dto.CategoryDTO;
import io.github.humbertoluiz.catalog.entities.Category;
import io.github.humbertoluiz.catalog.repositories.CategoryRepository;
import io.github.humbertoluiz.catalog.services.exception.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional( readOnly = true )
	public List<CategoryDTO> findAll() {
		List<Category> list = categoryRepository.findAll();
		return list.stream()
				.map(cat -> new CategoryDTO(cat))
				.collect(Collectors.toList());
	}
	
	@Transactional( readOnly = true )
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO catDTO) {
		Category entity = new Category();
		entity.setName(catDTO.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}		
}
