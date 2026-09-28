package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "volunteers")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VolunteerSkill skill;

    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VolunteerAvailability availability;

    private String assignedDisaster;

    public Volunteer() {
        this.availability = VolunteerAvailability.AVAILABLE;
    }

    public Volunteer(String name, String phone, String email, VolunteerSkill skill, String location, VolunteerAvailability availability, String assignedDisaster) {
        this();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.skill = skill;
        this.location = location;
        if (availability != null) {
            this.availability = availability;
        }
        this.assignedDisaster = assignedDisaster;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public VolunteerSkill getSkill() {
        return skill;
    }

    public void setSkill(VolunteerSkill skill) {
        this.skill = skill;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public VolunteerAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(VolunteerAvailability availability) {
        this.availability = availability;
    }

    public String getAssignedDisaster() {
        return assignedDisaster;
    }

    public void setAssignedDisaster(String assignedDisaster) {
        this.assignedDisaster = assignedDisaster;
    }
}
