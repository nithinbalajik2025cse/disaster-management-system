package com.example.demo.controllers;

import com.example.demo.models.Distribution;
import com.example.demo.services.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distributions")
public class DistributionController {

    private final DistributionService distributionService;

    @Autowired
    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @GetMapping
    public ResponseEntity<List<Distribution>> getAllDistributions() {
        return ResponseEntity.ok(distributionService.getAllDistributions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Distribution> getDistributionById(@PathVariable Long id) {
        return ResponseEntity.ok(distributionService.getDistributionById(id));
    }

    @PostMapping
    public ResponseEntity<Distribution> createDistribution(@RequestBody Distribution distribution) {
        Distribution created = distributionService.createDistribution(distribution);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Distribution> updateDistribution(@PathVariable Long id, @RequestBody Distribution distribution) {
        Distribution updated = distributionService.updateDistribution(id, distribution);
        return ResponseEntity.ok(updated);
    }
}
