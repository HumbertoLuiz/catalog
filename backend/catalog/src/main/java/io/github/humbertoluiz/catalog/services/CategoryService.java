package io.github.humbertoluiz.catalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.humbertoluiz.catalog.dto.CategoryDTO;
import io.github.humbertoluiz.catalog.entities.Category;
import io.github.humbertoluiz.catalog.repositories.CategoryRepository;

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
		
}
