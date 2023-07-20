package com.example.sdadvancedqueries.service;

import com.example.sdadvancedqueries.model.entity.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
