package com.fsd.sportyshoes.repositories;

import com.fsd.sportyshoes.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Objects;

public interface  UsersRepository extends  CrudRepository<Users, Integer> {
    Users findFirstByEmail(String email);

}
