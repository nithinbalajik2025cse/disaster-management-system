package com.example.demo.services;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Donation;
import com.example.demo.repositories.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation record not found with id: " + id));
    }

    public Donation createDonation(Donation donation) {
        if (donation.getDonorName() == null || donation.getDonorName().trim().isEmpty()) {
            throw new IllegalArgumentException("Donor name is required");
        }
        return donationRepository.save(donation);
    }

    public Donation updateDonation(Long id, Donation updated) {
        Donation existing = getDonationById(id);
        existing.setDonorName(updated.getDonorName());
        existing.setDonorEmail(updated.getDonorEmail());
        existing.setDonationType(updated.getDonationType());
        existing.setAmount(updated.getAmount());
        existing.setResourceName(updated.getResourceName());
        existing.setQuantity(updated.getQuantity());
        return donationRepository.save(existing);
    }

    public void deleteDonation(Long id) {
        Donation existing = getDonationById(id);
        donationRepository.delete(existing);
    }

    public double getTotalDonationAmount() {
        return donationRepository.sumTotalAmount();
    }
}
