package com.example.demo.repositories;

import com.example.demo.models.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    List<Allocation> findByRequestId(Long requestId);
    
    @Query("SELECT COALESCE(SUM(a.allocatedQuantity), 0) FROM Allocation a")
    long sumTotalAllocatedQuantity();
}
