package com.project.demo.logic.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<TblUser, Long>  {
    @Query("SELECT u FROM TblUser u WHERE LOWER(u.name) LIKE %?1%")
    List<TblUser> findUsersWithCharacterInName(String character);

    @Query("SELECT u FROM TblUser u WHERE u.name = ?1")
    Optional<TblUser> findByName(String name);

    /*Optional<TblUser> findByLastname(String lastName1);*/

    Optional<TblUser> findByEmail(String email);
}
