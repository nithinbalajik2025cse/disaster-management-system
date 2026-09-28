package com.example.demo.config;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
import com.example.demo.services.ResourceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DisasterRepository disasterRepository;
    private final ResourceRepository resourceRepository;
    private final ResourceRequestRepository requestRepository;
    private final ResourceRequestService requestService;
    private final VolunteerRepository volunteerRepository;
    private final DonationRepository donationRepository;

    @Autowired
    public DataInitializer(DisasterRepository disasterRepository,
                           ResourceRepository resourceRepository,
                           ResourceRequestRepository requestRepository,
                           ResourceRequestService requestService,
                           VolunteerRepository volunteerRepository,
                           DonationRepository donationRepository) {
        this.disasterRepository = disasterRepository;
        this.resourceRepository = resourceRepository;
        this.requestRepository = requestRepository;
        this.requestService = requestService;
        this.volunteerRepository = volunteerRepository;
        this.donationRepository = donationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (disasterRepository.count() == 0) {
            // Seed Disasters
            Disaster d1 = disasterRepository.save(new Disaster(
                    "Flood - Chennai", DisasterType.FLOOD, "Chennai Coastal North",
                    "Severe urban flooding due to heavy monsoon downpour.", PriorityLevel.CRITICAL, 500, 100
            ));

            Disaster d2 = disasterRepository.save(new Disaster(
                    "Cyclone - Coastal Area", DisasterType.CYCLONE, "Nagapattinam Coast",
                    "High intensity tropical cyclone causing infrastructural disruption.", PriorityLevel.HIGH, 300, 50
            ));

            Disaster d3 = disasterRepository.save(new Disaster(
                    "Earthquake - Northern Hills", DisasterType.EARTHQUAKE, "Himalayan Foothills",
                    "Moderate magnitude earthquake triggering minor landslides.", PriorityLevel.MEDIUM, 200, 30
            ));

            // Seed Resources
            Resource r1 = resourceRepository.save(new Resource(
                    "Drinking Water", ResourceCategory.WATER, 1250, "Litres", 300, "Depot A"
            ));

            Resource r2 = resourceRepository.save(new Resource(
                    "Medicine Kits", ResourceCategory.MEDICINE, 20, "Boxes", 50, "Central Pharmacy"
            ));

            Resource r3 = resourceRepository.save(new Resource(
                    "Blankets", ResourceCategory.CLOTHING, 15, "Packs", 40, "Depot B"
            ));

            Resource r4 = resourceRepository.save(new Resource(
                    "Food Packets", ResourceCategory.FOOD, 800, "Packs", 100, "Depot A"
            ));

            Resource r5 = resourceRepository.save(new Resource(
                    "Emergency Tents", ResourceCategory.SHELTER, 40, "Units", 15, "Depot C"
            ));

            // Seed Resource Requests
            ResourceRequest req1 = new ResourceRequest(d1.getId(), r1.getId(), 400, 100, 500, 5);
            requestService.createRequest(req1);

            ResourceRequest req2 = new ResourceRequest(d2.getId(), r2.getId(), 30, 50, 300, 10);
            requestService.createRequest(req2);

            ResourceRequest req3 = new ResourceRequest(d3.getId(), r3.getId(), 25, 30, 200, 15);
            requestService.createRequest(req3);

            // Seed Volunteers
            volunteerRepository.save(new Volunteer(
                    "Dr. Rajesh V", "+91 9876543210", "rajesh.v@relief.org", VolunteerSkill.MEDICAL,
                    "Chennai Central", VolunteerAvailability.AVAILABLE, "Flood - Chennai"
            ));

            volunteerRepository.save(new Volunteer(
                    "Suresh Kumar", "+91 9876543211", "suresh.k@relief.org", VolunteerSkill.RESCUE,
                    "Nagapattinam Base", VolunteerAvailability.AVAILABLE, "Cyclone - Coastal Area"
            ));

            volunteerRepository.save(new Volunteer(
                    "Priya Sharma", "+91 9876543212", "priya.s@relief.org", VolunteerSkill.FOOD_DISTRIBUTION,
                    "Chennai Depot A", VolunteerAvailability.AVAILABLE, "Unassigned"
            ));

            // Seed Donations
            donationRepository.save(new Donation(
                    "Tata Relief Trust", "contact@tata.org", DonationType.MONEY, 250000.0, "Disaster Fund", 1
            ));

            donationRepository.save(new Donation(
                    "Rotary International", "info@rotary.org", DonationType.WATER, 0.0, "Drinking Water Packs", 500
            ));
        }
    }
}
