package io.github.humbertoluiz.catalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.humbertoluiz.catalog.dto.CategoryDTO;
import io.github.humbertoluiz.catalog.dto.ProductDTO;
import io.github.humbertoluiz.catalog.entities.Category;
import io.github.humbertoluiz.catalog.entities.Product;
import io.github.humbertoluiz.catalog.repositories.CategoryRepository;
import io.github.humbertoluiz.catalog.repositories.ProductRepository;
import io.github.humbertoluiz.catalog.services.exceptions.DatabaseException;
import io.github.humbertoluiz.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional( readOnly = true )
	public Page<ProductDTO> findAllPaged(Pageable pageable) {
		Page<Product> list = productRepository.findAll(pageable);
		return list.map(cat -> new ProductDTO(cat));
	}
	
	@Transactional( readOnly = true )
	public ProductDTO findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO catDTO) {
		Product entity = new Product();
		copyDtoToEntity(catDTO, entity);
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(ProductDTO catDTO, Long id) {
		try {
			Product entity = productRepository.getReferenceById(id);
			copyDtoToEntity(catDTO, entity);
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	public void delete(Long id) {
		try {
		productRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}
	}
	
	private void copyDtoToEntity(ProductDTO catDTO, Product entity) {
		
		entity.setName(catDTO.getName());
		entity.setDescription(catDTO.getDescription());
		entity.setDate(catDTO.getDate());
		entity.setImgUrl(catDTO.getImgUrl());
		entity.setPrice(catDTO.getPrice());
		
		entity.getCategories().clear();
		for (CategoryDTO catDTO1 : catDTO.getCategories()) {
			Category category = categoryRepository.getReferenceById(catDTO1.getId());
			entity.getCategories().add(category);
		}
	}
}
