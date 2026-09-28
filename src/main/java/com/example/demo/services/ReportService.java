package com.example.demo.services;

import com.example.demo.models.DisasterStatus;
import com.example.demo.models.DistributionStatus;
import com.example.demo.models.RequestStatus;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    private final DisasterRepository disasterRepository;
    private final ResourceRepository resourceRepository;
    private final ResourceRequestRepository requestRepository;
    private final AllocationRepository allocationRepository;
    private final VolunteerRepository volunteerRepository;
    private final DonationRepository donationRepository;
    private final DistributionRepository distributionRepository;

    @Autowired
    public ReportService(DisasterRepository disasterRepository,
                         ResourceRepository resourceRepository,
                         ResourceRequestRepository requestRepository,
                         AllocationRepository allocationRepository,
                         VolunteerRepository volunteerRepository,
                         DonationRepository donationRepository,
                         DistributionRepository distributionRepository) {
        this.disasterRepository = disasterRepository;
        this.resourceRepository = resourceRepository;
        this.requestRepository = requestRepository;
        this.allocationRepository = allocationRepository;
        this.volunteerRepository = volunteerRepository;
        this.donationRepository = donationRepository;
        this.distributionRepository = distributionRepository;
    }

    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();

        long totalDisasters = disasterRepository.count();
        long activeDisasters = disasterRepository.countByStatus(DisasterStatus.ACTIVE);
        long totalResources = resourceRepository.count();
        long totalQuantityAvailable = resourceRepository.sumTotalQuantity();
        long lowStockItems = resourceRepository.countLowStockResources();
        long pendingRequests = requestRepository.countByStatus(RequestStatus.PENDING) + requestRepository.countByStatus(RequestStatus.PARTIALLY_ALLOCATED);
        long totalAllocatedQuantity = allocationRepository.sumTotalAllocatedQuantity();
        long totalVolunteers = volunteerRepository.count();
        double totalDonationsAmount = donationRepository.sumTotalAmount();
        long completedDistributions = distributionRepository.countByStatus(DistributionStatus.DISTRIBUTED);

        summary.put("totalDisasters", totalDisasters);
        summary.put("activeDisasters", activeDisasters);
        summary.put("totalResources", totalResources);
        summary.put("availableResources", totalQuantityAvailable);
        summary.put("lowStockItems", lowStockItems);
        summary.put("pendingRequests", pendingRequests);
        summary.put("allocatedResources", totalAllocatedQuantity);
        summary.put("totalVolunteers", totalVolunteers);
        summary.put("totalDonations", totalDonationsAmount);
        summary.put("completedDistributions", completedDistributions);

        return summary;
    }
}
