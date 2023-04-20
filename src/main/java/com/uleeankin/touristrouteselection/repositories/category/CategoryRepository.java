package com.uleeankin.touristrouteselection.repositories.category;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.uleeankin.touristrouteselection.models.activity.Category;

public interface CategoryRepository {

    List<Category> findAll();

    Optional<Category> findByName(String name);

    void save(String name);

}
