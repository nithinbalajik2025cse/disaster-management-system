package com.example.demo.services;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Volunteer;
import com.example.demo.models.VolunteerAvailability;
import com.example.demo.repositories.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Volunteer getVolunteerById(Long id) {
        return volunteerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found with id: " + id));
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        if (volunteer.getName() == null || volunteer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Volunteer name is required");
        }
        return volunteerRepository.save(volunteer);
    }

    public Volunteer updateVolunteer(Long id, Volunteer updated) {
        Volunteer existing = getVolunteerById(id);
        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        existing.setEmail(updated.getEmail());
        existing.setSkill(updated.getSkill());
        existing.setLocation(updated.getLocation());
        if (updated.getAvailability() != null) {
            existing.setAvailability(updated.getAvailability());
        }
        existing.setAssignedDisaster(updated.getAssignedDisaster());
        return volunteerRepository.save(existing);
    }

    public void deleteVolunteer(Long id) {
        Volunteer existing = getVolunteerById(id);
        volunteerRepository.delete(existing);
    }

    public List<Volunteer> getAvailableVolunteers() {
        return volunteerRepository.findByAvailability(VolunteerAvailability.AVAILABLE);
    }
}
