package com.example.emailVerificationPractice.Repository;

import com.example.emailVerificationPractice.Entity.EducationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationHistoryRepository extends JpaRepository<EducationHistory,Long> {

}
