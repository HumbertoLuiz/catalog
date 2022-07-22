package io.github.humbertoluiz.catalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.humbertoluiz.catalog.dto.ProductDTO;
import io.github.humbertoluiz.catalog.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
		// PARAMETROS: page, size, sort
		Page<ProductDTO> list = productService.findAllPaged(pageable);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById( @PathVariable Long id ) {
		ProductDTO catDTO = productService.findById(id);
		return ResponseEntity.ok().body(catDTO);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insert( @RequestBody ProductDTO catDTO ) {
		catDTO = productService.insert(catDTO);
		URI uri =	ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(catDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(catDTO);	
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update( @RequestBody ProductDTO catDTO, @PathVariable Long id ) {
		catDTO = productService.update(catDTO, id);
		return ResponseEntity.ok().body(catDTO);	
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> delete( @PathVariable Long id ) {
		productService.delete(id);
		return ResponseEntity.noContent().build();	
	}
}
