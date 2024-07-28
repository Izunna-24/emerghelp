package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
