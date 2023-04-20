package com.uleeankin.touristrouteselection.services.category;

import com.uleeankin.touristrouteselection.models.activity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    boolean save(String categoryName);
}
