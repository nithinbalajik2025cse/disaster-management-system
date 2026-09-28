package com.example.demo.controllers;

import com.example.demo.services.DisasterService;
import com.example.demo.services.ReportService;
import com.example.demo.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final ResourceService resourceService;
    private final DisasterService disasterService;

    @Autowired
    public ReportController(ReportService reportService, ResourceService resourceService, DisasterService disasterService) {
        this.reportService = reportService;
        this.resourceService = resourceService;
        this.disasterService = disasterService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardReport() {
        return ResponseEntity.ok(reportService.getDashboardSummary());
    }

    @GetMapping("/resources")
    public ResponseEntity<Object> getResourceReport() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @GetMapping("/disasters")
    public ResponseEntity<Object> getDisasterReport() {
        return ResponseEntity.ok(disasterService.getAllDisasters());
    }
}
