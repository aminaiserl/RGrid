package com.example.RGrid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RGrid.model.Users;
import com.example.RGrid.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	VerificationToken findByToken(String token);
	 
    VerificationToken findByUser(Users user);
}
