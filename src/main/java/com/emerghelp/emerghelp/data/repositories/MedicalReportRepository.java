package com.emerghelp.emerghelp.data.repositories;

import com.emerghelp.emerghelp.data.models.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long> {
}
