package com.example.recipes.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.User;
import com.example.recipes.repository.UserRepository;
import com.example.recipes.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

@RestController
public class RestAPI {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/recipe/new")
    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe) {
        Long id = recipeService.save(recipe);
        return Map.of("id", id);
    }


    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable @Pattern(regexp = "[0-9]{0,3}") Long id) {
        ResponseEntity<HttpStatus> responseEntity = recipeService.del(id);
        return responseEntity;
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<HttpStatus> putRecipe(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {
        ResponseEntity<HttpStatus> responseEntity = recipeService.put(id, recipe);
        return responseEntity;
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable String id) {
        Recipe recipe = null;
        try {
            recipe = recipeService.get(Long.valueOf(id));
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/api/recipe/search", params = "category")
    public ResponseEntity<?> getRecipesByCategory(@RequestParam(required = false) String[] category) {
        if (category.length == 1) {
            List<Recipe> recipes = recipeService.byCategory(category[0]);
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/api/recipe/search", params = "name")
    public ResponseEntity<?> getRecipesByName(@RequestParam String[] name) {
        if (name.length == 1) {
            List<Recipe> recipes = recipeService.byName(name[0]);
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/api/register")
    public ResponseEntity<HttpStatus> postUser(@Valid @RequestBody User user) {
        if (userRepo.findUserByEmail(user.getEmail()) == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
