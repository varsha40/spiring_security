package com.youtube.jwt.dao;

import com.youtube.jwt.entity.Role;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends MongoRepository<Role, String> {

	Optional<Role> findByRoleName(String roleName);

}
