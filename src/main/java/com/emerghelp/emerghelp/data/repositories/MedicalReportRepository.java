package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long> {
}
