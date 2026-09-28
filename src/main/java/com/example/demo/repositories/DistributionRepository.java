package com.example.demo.repositories;

import com.example.demo.models.Distribution;
import com.example.demo.models.DistributionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributionRepository extends JpaRepository<Distribution, Long> {
    List<Distribution> findByStatus(DistributionStatus status);
    long countByStatus(DistributionStatus status);
}
