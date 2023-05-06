package com.uleeankin.touristrouteselection.activity.attributes.category.service;

import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    boolean save(String categoryName);
}
