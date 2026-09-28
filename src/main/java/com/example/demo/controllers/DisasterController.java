package com.example.demo.controllers;

import com.example.demo.models.Disaster;
import com.example.demo.services.DisasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disasters")
public class DisasterController {

    private final DisasterService disasterService;

    @Autowired
    public DisasterController(DisasterService disasterService) {
        this.disasterService = disasterService;
    }

    @GetMapping
    public ResponseEntity<List<Disaster>> getAllDisasters() {
        return ResponseEntity.ok(disasterService.getAllDisasters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disaster> getDisasterById(@PathVariable Long id) {
        return ResponseEntity.ok(disasterService.getDisasterById(id));
    }

    @PostMapping
    public ResponseEntity<Disaster> createDisaster(@RequestBody Disaster disaster) {
        Disaster created = disasterService.createDisaster(disaster);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disaster> updateDisaster(@PathVariable Long id, @RequestBody Disaster disaster) {
        Disaster updated = disasterService.updateDisaster(id, disaster);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisaster(@PathVariable Long id) {
        disasterService.deleteDisaster(id);
        return ResponseEntity.noContent().build();
    }
}
