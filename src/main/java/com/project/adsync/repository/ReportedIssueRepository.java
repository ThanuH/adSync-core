package com.project.adsync.repository;

import com.project.adsync.domain.ReportedIssue;
import com.project.adsync.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportedIssueRepository extends JpaRepository<ReportedIssue, Integer>{

    @Query("SELECT count(*) FROM ReportedIssue r WHERE r.status = 'P'")
    int getPendingCount();

    @Query("SELECT r FROM ReportedIssue r WHERE r.status = 'P' AND r.user = :user")
    List<ReportedIssue> getUserWiseIssues(@Param("user") User user);
}
