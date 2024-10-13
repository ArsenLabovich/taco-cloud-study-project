package com.example.tacocloud.Repositories;

import com.example.tacocloud.Security.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Long> {
    User findByUsername(String username);
}
