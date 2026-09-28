package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resource_requests")
public class ResourceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "disaster_id", nullable = false)
    private Long disasterId;

    @Column(name = "resource_id", nullable = false)
    private Long resourceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disaster_id", insertable = false, updatable = false)
    private Disaster disaster;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", insertable = false, updatable = false)
    private Resource resource;

    @Column(nullable = false)
    private Integer requestedQuantity;

    private Integer approvedQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    private Integer criticalPeople;
    private Integer affectedPeople;
    private Integer waitingHours;

    private Double priorityScore;

    private LocalDateTime createdAt;

    public ResourceRequest() {
        this.createdAt = LocalDateTime.now();
        this.status = RequestStatus.PENDING;
        this.approvedQuantity = 0;
        this.criticalPeople = 0;
        this.affectedPeople = 0;
        this.waitingHours = 0;
        this.priorityScore = 0.0;
    }

    public ResourceRequest(Long disasterId, Long resourceId, Integer requestedQuantity, Integer criticalPeople, Integer affectedPeople, Integer waitingHours) {
        this();
        this.disasterId = disasterId;
        this.resourceId = resourceId;
        this.requestedQuantity = requestedQuantity;
        this.criticalPeople = criticalPeople != null ? criticalPeople : 0;
        this.affectedPeople = affectedPeople != null ? affectedPeople : 0;
        this.waitingHours = waitingHours != null ? waitingHours : 0;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = RequestStatus.PENDING;
        }
        if (this.approvedQuantity == null) {
            this.approvedQuantity = 0;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Integer getApprovedQuantity() {
        return approvedQuantity;
    }

    public void setApprovedQuantity(Integer approvedQuantity) {
        this.approvedQuantity = approvedQuantity;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Integer getCriticalPeople() {
        return criticalPeople;
    }

    public void setCriticalPeople(Integer criticalPeople) {
        this.criticalPeople = criticalPeople;
    }

    public Integer getAffectedPeople() {
        return affectedPeople;
    }

    public void setAffectedPeople(Integer affectedPeople) {
        this.affectedPeople = affectedPeople;
    }

    public Integer getWaitingHours() {
        return waitingHours;
    }

    public void setWaitingHours(Integer waitingHours) {
        this.waitingHours = waitingHours;
    }

    public Double getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(Double priorityScore) {
        this.priorityScore = priorityScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
