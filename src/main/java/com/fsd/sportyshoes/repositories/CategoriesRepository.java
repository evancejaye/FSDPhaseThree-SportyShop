package com.fsd.sportyshoes.repositories;
import  com.fsd.sportyshoes.models.Categories;
import org.springframework.data.repository.CrudRepository;

public interface CategoriesRepository extends CrudRepository<Categories, Integer> {
    Categories findById(Long categoryId);
}
