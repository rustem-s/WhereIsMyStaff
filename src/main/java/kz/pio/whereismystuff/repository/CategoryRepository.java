package kz.pio.whereismystuff.repository;

import kz.pio.whereismystuff.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    public Category getCategoryByName(String name);
    public Category findById(Long id);
}
