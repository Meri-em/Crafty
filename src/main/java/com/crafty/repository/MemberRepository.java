package com.crafty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crafty.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

}
