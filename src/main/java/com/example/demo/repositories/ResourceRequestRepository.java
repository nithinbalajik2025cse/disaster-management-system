package com.example.demo.repositories;

import com.example.demo.models.RequestStatus;
import com.example.demo.models.ResourceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRequestRepository extends JpaRepository<ResourceRequest, Long> {
    List<ResourceRequest> findByStatus(RequestStatus status);
    List<ResourceRequest> findByStatusIn(List<RequestStatus> statuses);
    long countByStatus(RequestStatus status);
}
