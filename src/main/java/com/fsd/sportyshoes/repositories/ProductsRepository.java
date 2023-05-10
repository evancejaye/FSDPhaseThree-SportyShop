package com.fsd.sportyshoes.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import  com.fsd.sportyshoes.models.Products;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Products, Integer> {
//    @Query("SELECT p FROM Products p WHERE p.category.id=:ids")
//    List<Products> findProductByCategoryId(@Param("categoryId") Long categoryId);

    List<Products> findProductsByCategory_Id(Long categoryId);

}
