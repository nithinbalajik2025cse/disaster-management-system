package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "disasters")
public class Disaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DisasterType type;

    private String location;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityLevel priorityLevel;

    private Integer affectedPeople;
    private Integer criticalPeople;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DisasterStatus status;

    public Disaster() {
        this.createdAt = LocalDateTime.now();
        this.status = DisasterStatus.ACTIVE;
        this.affectedPeople = 0;
        this.criticalPeople = 0;
    }

    public Disaster(String name, DisasterType type, String location, String description, PriorityLevel priorityLevel, Integer affectedPeople, Integer criticalPeople) {
        this();
        this.name = name;
        this.type = type;
        this.location = location;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.affectedPeople = affectedPeople != null ? affectedPeople : 0;
        this.criticalPeople = criticalPeople != null ? criticalPeople : 0;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = DisasterStatus.ACTIVE;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DisasterType getType() {
        return type;
    }

    public void setType(DisasterType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Integer getAffectedPeople() {
        return affectedPeople;
    }

    public void setAffectedPeople(Integer affectedPeople) {
        this.affectedPeople = affectedPeople;
    }

    public Integer getCriticalPeople() {
        return criticalPeople;
    }

    public void setCriticalPeople(Integer criticalPeople) {
        this.criticalPeople = criticalPeople;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DisasterStatus getStatus() {
        return status;
    }

    public void setStatus(DisasterStatus status) {
        this.status = status;
    }
}
