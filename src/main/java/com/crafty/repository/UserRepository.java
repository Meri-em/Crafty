package com.crafty.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crafty.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findById(String id);

	Optional<User> findByEmail(String email);

	Optional<User> findByMemberId(String memberId);

}
