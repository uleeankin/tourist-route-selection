package com.uleeankin.touristrouteselection.activity.attributes.category.service;

import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.activity.attributes.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAll() {
        return this.repository.findAll();
    }

    @Override
    public boolean save(String categoryName) {
        if (this.repository.findByName(categoryName).isEmpty()) {
            this.repository.save(categoryName);
            return true;
        }
        return false;
    }


}
