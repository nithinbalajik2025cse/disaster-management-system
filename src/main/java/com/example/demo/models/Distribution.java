package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "distributions")
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "allocation_id")
    private Long allocationId;

    @Column(name = "disaster_id")
    private Long disasterId;

    @Column(name = "resource_id")
    private Long resourceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "allocation_id", insertable = false, updatable = false)
    private Allocation allocation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disaster_id", insertable = false, updatable = false)
    private Disaster disaster;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", insertable = false, updatable = false)
    private Resource resource;

    @Column(nullable = false)
    private Integer quantity;

    private String distributedBy;

    private LocalDateTime distributedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DistributionStatus status;

    public Distribution() {
        this.distributedAt = LocalDateTime.now();
        this.status = DistributionStatus.PENDING;
    }

    public Distribution(Long allocationId, Long disasterId, Long resourceId, Integer quantity, String distributedBy, DistributionStatus status) {
        this();
        this.allocationId = allocationId;
        this.disasterId = disasterId;
        this.resourceId = resourceId;
        this.quantity = quantity;
        this.distributedBy = distributedBy;
        if (status != null) {
            this.status = status;
        }
    }

    @PrePersist
    protected void onCreate() {
        if (this.distributedAt == null) {
            this.distributedAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = DistributionStatus.PENDING;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Long allocationId) {
        this.allocationId = allocationId;
    }

    public Long getDisasterId() {
        return disasterId;
    }

    public void setDisasterId(Long disasterId) {
        this.disasterId = disasterId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Allocation getAllocation() {
        return allocation;
    }

    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
        if (allocation != null) {
            this.allocationId = allocation.getId();
        }
    }

    public Disaster getDisaster() {
        return disaster;
    }

    public void setDisaster(Disaster disaster) {
        this.disaster = disaster;
        if (disaster != null) {
            this.disasterId = disaster.getId();
        }
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
        if (resource != null) {
            this.resourceId = resource.getId();
        }
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDistributedBy() {
        return distributedBy;
    }

    public void setDistributedBy(String distributedBy) {
        this.distributedBy = distributedBy;
    }

    public LocalDateTime getDistributedAt() {
        return distributedAt;
    }

    public void setDistributedAt(LocalDateTime distributedAt) {
        this.distributedAt = distributedAt;
    }

    public DistributionStatus getStatus() {
        return status;
    }

    public void setStatus(DistributionStatus status) {
        this.status = status;
    }
}
