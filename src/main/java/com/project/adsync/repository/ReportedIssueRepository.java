package com.project.adsync.repository;

import com.project.adsync.domain.ReportedIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportedIssueRepository extends JpaRepository<ReportedIssue, Integer>{

    @Query("SELECT count(*) FROM ReportedIssue r WHERE r.status = 'P'")
    int getPendingCount();
}
