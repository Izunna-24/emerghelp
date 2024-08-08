package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT U FROM User U WHERE U.email =:email")
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    User findByEmailIgnoreCase(String email);
}
