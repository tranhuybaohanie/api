package com.example.api.dao;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.api.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}