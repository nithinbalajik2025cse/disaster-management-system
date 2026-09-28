package com.example.demo.repositories;

import com.example.demo.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("SELECT r FROM Resource r WHERE r.quantity <= r.minimumStock")
    List<Resource> findLowStockResources();

    @Query("SELECT COUNT(r) FROM Resource r WHERE r.quantity <= r.minimumStock")
    long countLowStockResources();

    @Query("SELECT COALESCE(SUM(r.quantity), 0) FROM Resource r")
    long sumTotalQuantity();
}
