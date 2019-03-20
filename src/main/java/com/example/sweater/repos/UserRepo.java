package com.example.sweater.repos;

import com.example.sweater.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepo extends JpaRepository<User, Long> {
  User findByUsername(String username);

  User findByActivationCode(String code);

  @Transactional
  @Modifying
  @Query("DELETE from User u WHERE u.username = :username and u.active = false")
  void deleteNonActivatedUser(@Param("username") String username);

}
