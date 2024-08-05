package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
