package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.ProgressReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressReportRepository extends JpaRepository<ProgressReport, Long> {
}
