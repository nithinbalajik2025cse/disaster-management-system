package com.example.demo.repositories;

import com.example.demo.models.Volunteer;
import com.example.demo.models.VolunteerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    List<Volunteer> findByAvailability(VolunteerAvailability availability);
}
