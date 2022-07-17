package io.github.humbertoluiz.catalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.github.humbertoluiz.catalog.dto.CategoryDTO;
import io.github.humbertoluiz.catalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> list = categoryService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById( @PathVariable Long id ) {
		CategoryDTO catDTO = categoryService.findById(id);
		return ResponseEntity.ok().body(catDTO);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert( @RequestBody CategoryDTO catDTO ) {
		catDTO = categoryService.insert(catDTO);
		URI uri =	ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(catDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(catDTO);	
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update( @RequestBody CategoryDTO catDTO, @PathVariable Long id ) {
		catDTO = categoryService.update(catDTO, id);
		return ResponseEntity.ok().body(catDTO);	
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update( @PathVariable Long id ) {
		categoryService.delete(id);
		return ResponseEntity.noContent().build();	
	}
}
