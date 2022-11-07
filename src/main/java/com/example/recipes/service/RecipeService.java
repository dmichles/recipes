package com.example.recipes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.User;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;

    public Long save(Recipe recipe){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findUserByEmail(email);
        recipe.setUser(user);
        recipeRepository.save(recipe);
        Recipe recipe1 = recipeRepository.findRecipeByName(recipe.getName());
        return recipe1.getId();
    }

    public Recipe get(Long id){
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            return null;
        } else {
            return recipe.get();
        }
    }

    public ResponseEntity<HttpStatus> del(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        Recipe recipe = recipeRepository.findRecipeById(id);
        User user = userRepository.findUserByEmail(email);
        User user1 = recipe.getUser();
        if (user1.getUserid() != user.getUserid()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> put(Long id, Recipe recipe){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        Recipe recipe1 = recipeRepository.findRecipeById(id);
        User user = userRepository.findUserByEmail(email);
        User user1 = recipe1.getUser();
        if (user1.getUserid() != user.getUserid()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (recipeRepository.existsById(id)){

            recipe1.setName(recipe.getName());
            recipe1.setDescription(recipe.getDescription());
            recipe1.setIngredients(recipe.getIngredients());
            recipe1.setDirections(recipe.getDirections());
            recipeRepository.save(recipe1);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public List<Recipe> byCategory(String category){
        List<Recipe> recipes = recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
        return recipes;
    }

    public List<Recipe> byName(String name){
        List<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
        return recipes;
    }

}
