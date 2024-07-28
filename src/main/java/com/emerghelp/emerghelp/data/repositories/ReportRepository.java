package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
