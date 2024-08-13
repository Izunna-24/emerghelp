package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMessage extends JpaRepository<User, Long> {
}
