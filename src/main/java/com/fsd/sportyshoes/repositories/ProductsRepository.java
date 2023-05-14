package com.fsd.sportyshoes.repositories;

import org.springframework.data.repository.CrudRepository;
import  com.fsd.sportyshoes.models.Products;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Products, Integer> {

    List<Products> findProductsByCategory_Id(Long categoryId);
    Products findFirstById(Long productId);

}
