package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String donorName;

    private String donorEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationType donationType;

    private Double amount;
    private String resourceName;
    private Integer quantity;

    private LocalDateTime donatedAt;

    public Donation() {
        this.donatedAt = LocalDateTime.now();
        this.amount = 0.0;
        this.quantity = 0;
    }

    public Donation(String donorName, String donorEmail, DonationType donationType, Double amount, String resourceName, Integer quantity) {
        this();
        this.donorName = donorName;
        this.donorEmail = donorEmail;
        this.donationType = donationType;
        this.amount = amount != null ? amount : 0.0;
        this.resourceName = resourceName;
        this.quantity = quantity != null ? quantity : 0;
    }

    @PrePersist
    protected void onCreate() {
        if (this.donatedAt == null) {
            this.donatedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorEmail() {
        return donorEmail;
    }

    public void setDonorEmail(String donorEmail) {
        this.donorEmail = donorEmail;
    }

    public DonationType getDonationType() {
        return donationType;
    }

    public void setDonationType(DonationType donationType) {
        this.donationType = donationType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDonatedAt() {
        return donatedAt;
    }

    public void setDonatedAt(LocalDateTime donatedAt) {
        this.donatedAt = donatedAt;
    }
}
