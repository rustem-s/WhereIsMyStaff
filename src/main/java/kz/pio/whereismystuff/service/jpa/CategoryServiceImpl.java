package kz.pio.whereismystuff.service.jpa;

import com.google.common.collect.Sets;
import kz.pio.whereismystuff.domain.Category;
import kz.pio.whereismystuff.repository.CategoryRepository;
import kz.pio.whereismystuff.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Service for Category entity
 * @version 20140614
 * @author Rustem S
 */
@Service("categoryService")
@Repository
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Set<Category> findAll() {
        return Sets.newHashSet(categoryRepository.findAll());
    }
    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.getCategoryByName(name);
    }
    @Override
    public Category findById(Long id) {
        return categoryRepository.findOne(id);
    }

}
