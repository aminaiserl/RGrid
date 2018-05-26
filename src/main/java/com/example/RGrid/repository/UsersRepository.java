package com.example.RGrid.repository;

import java.util.Optional;

import com.example.RGrid.model.Users;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Integer>{

	Optional<Users> findByUserName(String username);

}
