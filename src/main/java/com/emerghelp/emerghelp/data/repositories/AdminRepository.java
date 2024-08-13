package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT U FROM Admin U WHERE U.email =:email")
    Admin findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Admin findAdminByUserName(String userName);
}
