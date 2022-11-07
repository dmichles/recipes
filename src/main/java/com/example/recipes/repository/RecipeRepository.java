package com.example.recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.recipes.model.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long>{
    public Recipe findRecipeById(Long id);

    public List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    public List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);

    public Recipe findRecipeByName(String name);
}
