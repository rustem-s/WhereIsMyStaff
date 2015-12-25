package kz.pio.whereismystuff.service;

import kz.pio.whereismystuff.domain.Category;

import java.util.Set;

public interface CategoryService {
    public Set<Category> findAll();
    public Category getCategoryByName(String name);
    public Category findById(Long id);
}
