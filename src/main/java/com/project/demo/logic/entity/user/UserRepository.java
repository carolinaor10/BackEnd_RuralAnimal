package com.project.demo.logic.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<TblUser, Long>  {
    Optional<TblUser> findByEmail(String email);
}
