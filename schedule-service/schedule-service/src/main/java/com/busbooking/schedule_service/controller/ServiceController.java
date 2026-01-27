package com.busbooking.schedule_service.controller;

import com.busbooking.schedule_service.dto.ServiceRequestDTO;
import com.busbooking.schedule_service.dto.ServiceResponseDTO;
import com.busbooking.schedule_service.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<ServiceResponseDTO> createService(
            @RequestBody ServiceRequestDTO request) {

        return ResponseEntity.ok(serviceService.createService(request));
    }

    // ================= UPDATE =================
    @PutMapping
    public ResponseEntity<ServiceResponseDTO> updateService(
            @RequestBody ServiceRequestDTO request) {

        return ResponseEntity.ok(serviceService.updateService(request));
    }

    // ================= FETCH =================
    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> getService(
            @PathVariable Long serviceId) {

        return ResponseEntity.ok(serviceService.getService(serviceId));
    }

    // ================= DELETE =================
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<String> deleteService(
            @PathVariable Long serviceId) {

        serviceService.deleteService(serviceId);
        return ResponseEntity.ok("Service deleted successfully");
    }
}
