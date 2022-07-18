package io.github.humbertoluiz.catalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.humbertoluiz.catalog.dto.ProductDTO;
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
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = productRepository.findAll(pageRequest);
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
		entity.setName(catDTO.getName());
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(ProductDTO catDTO, Long id) {
		try {
			Product entity = productRepository.getReferenceById(id);
			entity.setName(catDTO.getName());
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	public void delete(Long id) {
		try {
		categoryRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}
	}		
}
