package com.fsd.sportyshoes.repositories;

import com.fsd.sportyshoes.models.CustomerOrders;
import com.fsd.sportyshoes.models.Products;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerOrdersRepository extends CrudRepository<CustomerOrders, Integer> {
    List<CustomerOrders> findCustomerOrdersByUser_Id(Long userId);
}
