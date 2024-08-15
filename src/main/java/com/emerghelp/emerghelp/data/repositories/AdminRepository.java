package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT U FROM Admin U WHERE U.email =:email")
    boolean existsByEmail(String email);
}
