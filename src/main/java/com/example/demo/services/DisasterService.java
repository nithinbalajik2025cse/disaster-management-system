package com.example.demo.services;

import com.example.demo.exceptions.DisasterNotFoundException;
import com.example.demo.models.Disaster;
import com.example.demo.models.DisasterStatus;
import com.example.demo.repositories.DisasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisasterService {

    private final DisasterRepository disasterRepository;

    @Autowired
    public DisasterService(DisasterRepository disasterRepository) {
        this.disasterRepository = disasterRepository;
    }

    public List<Disaster> getAllDisasters() {
        return disasterRepository.findAll();
    }

    public Disaster getDisasterById(Long id) {
        return disasterRepository.findById(id)
                .orElseThrow(() -> new DisasterNotFoundException("Disaster not found with id: " + id));
    }

    public Disaster createDisaster(Disaster disaster) {
        if (disaster.getName() == null || disaster.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Disaster name is required");
        }
        return disasterRepository.save(disaster);
    }

    public Disaster updateDisaster(Long id, Disaster updatedDisaster) {
        Disaster existing = getDisasterById(id);
        existing.setName(updatedDisaster.getName());
        existing.setType(updatedDisaster.getType());
        existing.setLocation(updatedDisaster.getLocation());
        existing.setDescription(updatedDisaster.getDescription());
        existing.setPriorityLevel(updatedDisaster.getPriorityLevel());
        existing.setAffectedPeople(updatedDisaster.getAffectedPeople());
        existing.setCriticalPeople(updatedDisaster.getCriticalPeople());
        if (updatedDisaster.getStatus() != null) {
            existing.setStatus(updatedDisaster.getStatus());
        }
        return disasterRepository.save(existing);
    }

    public void deleteDisaster(Long id) {
        Disaster existing = getDisasterById(id);
        disasterRepository.delete(existing);
    }

    public List<Disaster> getActiveDisasters() {
        return disasterRepository.findByStatus(DisasterStatus.ACTIVE);
    }
}
