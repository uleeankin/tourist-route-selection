package com.uleeankin.touristrouteselection.activity.attributes.category.repository;

import java.util.List;
import java.util.Optional;

import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;

public interface CategoryRepository {

    List<Category> findAll();

    Optional<Category> findByName(String name);

    void save(String name);

}
