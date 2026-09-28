package com.example.demo;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
import com.example.demo.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {

	@Autowired
	private DisasterService disasterService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ResourceRequestService requestService;

	@Autowired
	private AllocationService allocationService;

	@Autowired
	private VolunteerService volunteerService;

	@Autowired
	private DonationService donationService;

	@Autowired
	private DistributionRepository distributionRepository;

	@Autowired
	private AllocationRepository allocationRepository;

	@Autowired
	private ResourceRequestRepository requestRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private DisasterRepository disasterRepository;

	@Autowired
	private VolunteerRepository volunteerRepository;

	@Autowired
	private DonationRepository donationRepository;

	private Disaster criticalDisaster;
	private Disaster highDisaster;
	private Resource waterResource;

	@BeforeEach
	void setUp() {
		// Clean up existing data in reverse dependency order (child tables first)
		distributionRepository.deleteAll();
		allocationRepository.deleteAll();
		requestRepository.deleteAll();
		resourceRepository.deleteAll();
		disasterRepository.deleteAll();
		volunteerRepository.deleteAll();
		donationRepository.deleteAll();

		// Create test disasters
		criticalDisaster = disasterService.createDisaster(
				new Disaster("Flood - Chennai", DisasterType.FLOOD, "Chennai Coastal", "Critical flooding", PriorityLevel.CRITICAL, 500, 100)
		);

		highDisaster = disasterService.createDisaster(
				new Disaster("Cyclone - Coastal Area", DisasterType.CYCLONE, "Nagapattinam", "High cyclone damage", PriorityLevel.HIGH, 300, 50)
		);

		// Create test resource
		waterResource = resourceService.createResource(
				new Resource("Drinking Water", ResourceCategory.WATER, 150, "Litres", 50, "Depot A")
		);
	}

	@Test
	@DisplayName("1. Verify Spring Context Loads Successfully")
	void contextLoads() {
		assertNotNull(disasterService);
		assertNotNull(resourceService);
		assertNotNull(requestService);
		assertNotNull(allocationService);
	}

	@Test
	@DisplayName("2. Test Priority Score Formula Calculation")
	void testPriorityScoreCalculation() {
		// Formula: (Priority Weight * 40) + (Critical People * 30) + (Affected People * 20) + (Waiting Hours * 10)
		// CRITICAL Weight = 4. Critical = 100, Affected = 500, Waiting = 5.
		// Expected Score = (4 * 40) + (100 * 30) + (500 * 20) + (5 * 10) = 160 + 3000 + 10000 + 50 = 13210.0
		double score = requestService.calculatePriorityScore(PriorityLevel.CRITICAL, 100, 500, 5);
		assertEquals(13210.0, score, 0.001);
	}

	@Test
	@DisplayName("3. Test Smart Allocation Engine Priority Queue Ordering & Partial Allocation")
	void testSmartAllocationPriorityQueue() {
		// Request A (CRITICAL priority, 100 requested)
		ResourceRequest reqA = new ResourceRequest(criticalDisaster.getId(), waterResource.getId(), 100, 100, 500, 5);
		reqA = requestService.createRequest(reqA);

		// Request B (HIGH priority, 100 requested)
		ResourceRequest reqB = new ResourceRequest(highDisaster.getId(), waterResource.getId(), 100, 50, 300, 10);
		reqB = requestService.createRequest(reqB);

		assertTrue(reqA.getPriorityScore() > reqB.getPriorityScore());

		// Execute Smart Allocation Engine
		List<Allocation> allocations = allocationService.runSmartAllocation();
		assertEquals(2, allocations.size());

		// Total available water was 150.
		// Request A (highest score) gets full 100 units. Remaining inventory = 50.
		// Request B (second highest score) gets remaining 50 units (PARTIAL). Remaining inventory = 0.
		ResourceRequest updatedReqA = requestService.getRequestById(reqA.getId());
		ResourceRequest updatedReqB = requestService.getRequestById(reqB.getId());

		assertEquals(100, updatedReqA.getApprovedQuantity());
		assertEquals(RequestStatus.ALLOCATED, updatedReqA.getStatus());

		assertEquals(50, updatedReqB.getApprovedQuantity());
		assertEquals(RequestStatus.PARTIALLY_ALLOCATED, updatedReqB.getStatus());

		Resource updatedResource = resourceService.getResourceById(waterResource.getId());
		assertEquals(0, updatedResource.getQuantity());
		assertTrue(updatedResource.isLowStock());
	}

	@Test
	@DisplayName("4. Test Volunteer & Donation Registration")
	void testVolunteerAndDonation() {
		Volunteer volunteer = volunteerService.createVolunteer(
				new Volunteer("Anita Sharma", "+919876543210", "anita@relief.org", VolunteerSkill.MEDICAL, "Chennai", VolunteerAvailability.AVAILABLE, "Flood - Chennai")
		);
		assertNotNull(volunteer.getId());

		Donation donation = donationService.createDonation(
				new Donation("Global Red Cross", "contact@redcross.org", DonationType.MONEY, 50000.0, "Relief Fund", 1)
		);
		assertNotNull(donation.getId());
		assertTrue(donationService.getTotalDonationAmount() >= 50000.0);
	}
}
