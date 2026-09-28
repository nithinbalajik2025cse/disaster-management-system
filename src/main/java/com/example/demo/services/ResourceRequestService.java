package com.example.demo.services;

import com.example.demo.exceptions.DisasterNotFoundException;
import com.example.demo.exceptions.RequestNotFoundException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.*;
import com.example.demo.repositories.DisasterRepository;
import com.example.demo.repositories.ResourceRepository;
import com.example.demo.repositories.ResourceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceRequestService {

    private final ResourceRequestRepository requestRepository;
    private final DisasterRepository disasterRepository;
    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceRequestService(ResourceRequestRepository requestRepository,
                                  DisasterRepository disasterRepository,
                                  ResourceRepository resourceRepository) {
        this.requestRepository = requestRepository;
        this.disasterRepository = disasterRepository;
        this.resourceRepository = resourceRepository;
    }

    public List<ResourceRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public ResourceRequest getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Resource Request not found with id: " + id));
    }

    public ResourceRequest createRequest(ResourceRequest request) {
        Disaster disaster = disasterRepository.findById(request.getDisasterId())
                .orElseThrow(() -> new DisasterNotFoundException("Disaster not found with id: " + request.getDisasterId()));
        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + request.getResourceId()));

        request.setDisaster(disaster);
        request.setResource(resource);

        if (request.getCriticalPeople() == null || request.getCriticalPeople() == 0) {
            request.setCriticalPeople(disaster.getCriticalPeople());
        }
        if (request.getAffectedPeople() == null || request.getAffectedPeople() == 0) {
            request.setAffectedPeople(disaster.getAffectedPeople());
        }

        double score = calculatePriorityScore(disaster.getPriorityLevel(), request.getCriticalPeople(), request.getAffectedPeople(), request.getWaitingHours());
        request.setPriorityScore(score);

        return requestRepository.save(request);
    }

    public ResourceRequest updateRequest(Long id, ResourceRequest updated) {
        ResourceRequest existing = getRequestById(id);
        existing.setRequestedQuantity(updated.getRequestedQuantity());
        if (updated.getApprovedQuantity() != null) {
            existing.setApprovedQuantity(updated.getApprovedQuantity());
        }
        if (updated.getCriticalPeople() != null) {
            existing.setCriticalPeople(updated.getCriticalPeople());
        }
        if (updated.getAffectedPeople() != null) {
            existing.setAffectedPeople(updated.getAffectedPeople());
        }
        if (updated.getWaitingHours() != null) {
            existing.setWaitingHours(updated.getWaitingHours());
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }

        Disaster disaster = disasterRepository.findById(existing.getDisasterId()).orElse(null);
        PriorityLevel priorityLevel = (disaster != null) ? disaster.getPriorityLevel() : PriorityLevel.MEDIUM;
        double score = calculatePriorityScore(priorityLevel, existing.getCriticalPeople(), existing.getAffectedPeople(), existing.getWaitingHours());
        existing.setPriorityScore(score);

        return requestRepository.save(existing);
    }

    public void deleteRequest(Long id) {
        ResourceRequest existing = getRequestById(id);
        requestRepository.delete(existing);
    }

    public double calculatePriorityScore(PriorityLevel priorityLevel, Integer criticalPeople, Integer affectedPeople, Integer waitingHours) {
        int weight = (priorityLevel != null) ? priorityLevel.getWeight() : 1;
        int critical = (criticalPeople != null) ? criticalPeople : 0;
        int affected = (affectedPeople != null) ? affectedPeople : 0;
        int waiting = (waitingHours != null) ? waitingHours : 0;

        return (weight * 40.0) + (critical * 30.0) + (affected * 20.0) + (waiting * 10.0);
    }
}
