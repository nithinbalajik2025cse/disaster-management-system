package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "allocations")
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_id", nullable = false)
    private Long requestId;

    @Column(name = "resource_id", nullable = false)
    private Long resourceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id", insertable = false, updatable = false)
    private ResourceRequest request;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", insertable = false, updatable = false)
    private Resource resource;

    @Column(nullable = false)
    private Integer allocatedQuantity;

    private Double priorityScore;

    private LocalDateTime allocatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AllocationStatus status;

    public Allocation() {
        this.allocatedAt = LocalDateTime.now();
        this.status = AllocationStatus.ALLOCATED;
    }

    public Allocation(Long requestId, Long resourceId, Integer allocatedQuantity, Double priorityScore, AllocationStatus status) {
        this();
        this.requestId = requestId;
        this.resourceId = resourceId;
        this.allocatedQuantity = allocatedQuantity;
        this.priorityScore = priorityScore;
        this.status = status != null ? status : AllocationStatus.ALLOCATED;
    }

    @PrePersist
    protected void onCreate() {
        if (this.allocatedAt == null) {
            this.allocatedAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = AllocationStatus.ALLOCATED;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public ResourceRequest getRequest() {
        return request;
    }

    public void setRequest(ResourceRequest request) {
        this.request = request;
        if (request != null) {
            this.requestId = request.getId();
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

    public Integer getAllocatedQuantity() {
        return allocatedQuantity;
    }

    public void setAllocatedQuantity(Integer allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
    }

    public Double getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(Double priorityScore) {
        this.priorityScore = priorityScore;
    }

    public LocalDateTime getAllocatedAt() {
        return allocatedAt;
    }

    public void setAllocatedAt(LocalDateTime allocatedAt) {
        this.allocatedAt = allocatedAt;
    }

    public AllocationStatus getStatus() {
        return status;
    }

    public void setStatus(AllocationStatus status) {
        this.status = status;
    }
}
