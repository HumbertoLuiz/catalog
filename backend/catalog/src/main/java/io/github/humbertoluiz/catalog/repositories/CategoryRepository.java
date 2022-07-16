package io.github.humbertoluiz.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.humbertoluiz.catalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
