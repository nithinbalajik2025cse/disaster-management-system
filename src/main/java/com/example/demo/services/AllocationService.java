package com.example.demo.services;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.*;
import com.example.demo.repositories.AllocationRepository;
import com.example.demo.repositories.DisasterRepository;
import com.example.demo.repositories.ResourceRepository;
import com.example.demo.repositories.ResourceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AllocationService {

    private final AllocationRepository allocationRepository;
    private final ResourceRequestRepository requestRepository;
    private final ResourceRepository resourceRepository;
    private final DisasterRepository disasterRepository;

    @Autowired
    public AllocationService(AllocationRepository allocationRepository,
                             ResourceRequestRepository requestRepository,
                             ResourceRepository resourceRepository,
                             DisasterRepository disasterRepository) {
        this.allocationRepository = allocationRepository;
        this.requestRepository = requestRepository;
        this.resourceRepository = resourceRepository;
        this.disasterRepository = disasterRepository;
    }

    public List<Allocation> getAllAllocations() {
        return allocationRepository.findAll();
    }

    public Allocation getAllocationById(Long id) {
        return allocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allocation record not found with id: " + id));
    }

    /**
     * Smart Priority-Based Resource Allocation Algorithm.
     * Uses Java PriorityQueue to process pending requests in order of priority score.
     */
    @Transactional
    public List<Allocation> runSmartAllocation() {
        // 1. Fetch all pending and partially allocated requests
        List<ResourceRequest> pendingRequests = requestRepository.findByStatusIn(
                List.of(RequestStatus.PENDING, RequestStatus.PARTIALLY_ALLOCATED)
        );

        if (pendingRequests.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. Calculate Priority Score for each request using Formula
        for (ResourceRequest req : pendingRequests) {
            Disaster disaster = disasterRepository.findById(req.getDisasterId()).orElse(null);
            PriorityLevel priorityLevel = (disaster != null) ? disaster.getPriorityLevel() : PriorityLevel.MEDIUM;

            int weight = priorityLevel.getWeight();
            int critical = req.getCriticalPeople() != null ? req.getCriticalPeople() : 0;
            int affected = req.getAffectedPeople() != null ? req.getAffectedPeople() : 0;
            int waiting = req.getWaitingHours() != null ? req.getWaitingHours() : 0;

            double score = (weight * 40.0) + (critical * 30.0) + (affected * 20.0) + (waiting * 10.0);
            req.setPriorityScore(score);
            requestRepository.save(req);
        }

        // 3. Insert requests into PriorityQueue (Highest priority score comes first)
        PriorityQueue<ResourceRequest> priorityQueue = new PriorityQueue<>(
                (r1, r2) -> Double.compare(r2.getPriorityScore(), r1.getPriorityScore())
        );
        priorityQueue.addAll(pendingRequests);

        List<Allocation> newAllocations = new ArrayList<>();

        // 4. Process requests from PriorityQueue until queue is empty
        while (!priorityQueue.isEmpty()) {
            ResourceRequest req = priorityQueue.poll();

            Optional<Resource> optionalResource = resourceRepository.findById(req.getResourceId());
            if (optionalResource.isEmpty()) {
                continue; // Skip if requested resource no longer exists
            }

            Resource resource = optionalResource.get();
            int availableQuantity = resource.getQuantity() != null ? resource.getQuantity() : 0;

            if (availableQuantity <= 0) {
                continue; // Inventory exhausted for this resource
            }

            int alreadyApproved = req.getApprovedQuantity() != null ? req.getApprovedQuantity() : 0;
            int neededQuantity = req.getRequestedQuantity() - alreadyApproved;

            if (neededQuantity <= 0) {
                req.setStatus(RequestStatus.ALLOCATED);
                requestRepository.save(req);
                continue;
            }

            // Determine quantity to allocate
            int allocatedQuantity = Math.min(neededQuantity, availableQuantity);

            // Update inventory
            resource.setQuantity(availableQuantity - allocatedQuantity);
            resourceRepository.save(resource);

            // Update request
            int newApprovedQuantity = alreadyApproved + allocatedQuantity;
            req.setApprovedQuantity(newApprovedQuantity);

            AllocationStatus allocationStatus;
            if (newApprovedQuantity >= req.getRequestedQuantity()) {
                req.setStatus(RequestStatus.ALLOCATED);
                allocationStatus = AllocationStatus.ALLOCATED;
            } else {
                req.setStatus(RequestStatus.PARTIALLY_ALLOCATED);
                allocationStatus = AllocationStatus.PARTIAL;
            }
            requestRepository.save(req);

            // Create Allocation Record
            Allocation allocation = new Allocation(
                    req.getId(),
                    resource.getId(),
                    allocatedQuantity,
                    req.getPriorityScore(),
                    allocationStatus
            );
            allocation.setRequest(req);
            allocation.setResource(resource);

            Allocation savedAllocation = allocationRepository.save(allocation);
            newAllocations.add(savedAllocation);
        }

        return newAllocations;
    }
}
