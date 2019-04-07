package com.crafty.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crafty.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

	Optional<Member> findById(String id);
}
