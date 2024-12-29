package com.javapersistence.mapping.repositories;

import com.javapersistence.mapping.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}