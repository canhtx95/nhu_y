package com.myapp.canhvm.repository;

import com.myapp.canhvm.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findById(Integer id);
    Optional<User> findByUsername(String name);

    List<User> findAll();

//    User save(User user);
}
