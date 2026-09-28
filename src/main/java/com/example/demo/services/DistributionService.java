package com.example.demo.services;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.*;
import com.example.demo.repositories.AllocationRepository;
import com.example.demo.repositories.DisasterRepository;
import com.example.demo.repositories.DistributionRepository;
import com.example.demo.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributionService {

    private final DistributionRepository distributionRepository;
    private final AllocationRepository allocationRepository;
    private final DisasterRepository disasterRepository;
    private final ResourceRepository resourceRepository;

    @Autowired
    public DistributionService(DistributionRepository distributionRepository,
                               AllocationRepository allocationRepository,
                               DisasterRepository disasterRepository,
                               ResourceRepository resourceRepository) {
        this.distributionRepository = distributionRepository;
        this.allocationRepository = allocationRepository;
        this.disasterRepository = disasterRepository;
        this.resourceRepository = resourceRepository;
    }

    public List<Distribution> getAllDistributions() {
        return distributionRepository.findAll();
    }

    public Distribution getDistributionById(Long id) {
        return distributionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Distribution record not found with id: " + id));
    }

    public Distribution createDistribution(Distribution distribution) {
        if (distribution.getAllocationId() != null) {
            Allocation allocation = allocationRepository.findById(distribution.getAllocationId()).orElse(null);
            if (allocation != null) {
                distribution.setAllocation(allocation);
                if (distribution.getResourceId() == null) {
                    distribution.setResourceId(allocation.getResourceId());
                }
                if (distribution.getQuantity() == null) {
                    distribution.setQuantity(allocation.getAllocatedQuantity());
                }
            }
        }
        if (distribution.getDisasterId() != null) {
            Disaster disaster = disasterRepository.findById(distribution.getDisasterId()).orElse(null);
            if (disaster != null) {
                distribution.setDisaster(disaster);
            }
        }
        if (distribution.getResourceId() != null) {
            Resource resource = resourceRepository.findById(distribution.getResourceId()).orElse(null);
            if (resource != null) {
                distribution.setResource(resource);
            }
        }

        return distributionRepository.save(distribution);
    }

    public Distribution updateDistribution(Long id, Distribution updated) {
        Distribution existing = getDistributionById(id);
        if (updated.getQuantity() != null) {
            existing.setQuantity(updated.getQuantity());
        }
        if (updated.getDistributedBy() != null) {
            existing.setDistributedBy(updated.getDistributedBy());
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        return distributionRepository.save(existing);
    }
}
