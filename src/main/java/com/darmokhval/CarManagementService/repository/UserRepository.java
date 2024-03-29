package com.darmokhval.CarManagementService.repository;

import com.darmokhval.CarManagementService.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

//    @Query("SELECT u FROM User u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
//    Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
}
