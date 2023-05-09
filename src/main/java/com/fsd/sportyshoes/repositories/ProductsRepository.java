package com.fsd.sportyshoes.repositories;

import org.springframework.data.repository.CrudRepository;
import  com.fsd.sportyshoes.models.Products;

public interface ProductsRepository extends CrudRepository<Products, Integer> {
}
