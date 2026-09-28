package com.example.demo.repositories;

import com.example.demo.models.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    
    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d WHERE d.donationType = com.example.demo.models.DonationType.MONEY")
    double sumTotalMonetaryDonations();

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d")
    double sumTotalAmount();
}
