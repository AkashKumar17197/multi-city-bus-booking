package com.busbooking.schedule_service.controller;

import com.busbooking.schedule_service.dto.ScheduleSearchResponse;
import com.busbooking.schedule_service.dto.ServiceRequestDTO;
import com.busbooking.schedule_service.dto.ServiceResponseDTO;
import com.busbooking.schedule_service.repository.ServiceScheduleRepository;
import com.busbooking.schedule_service.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;
    private final ServiceScheduleRepository scheduleRepository;

    public ServiceController(ServiceService serviceService, ServiceScheduleRepository scheduleRepository) {
        this.serviceService = serviceService;
        this.scheduleRepository = scheduleRepository;
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

    /**
     * SEARCH API (used by searchbuses service)
     *
     * Example:
     * GET /api/schedules/search?routeId=6&directionSeq=2
     */
    @GetMapping("/search")
    public List<ScheduleSearchResponse> searchSchedules(
            @RequestParam Long routeId,
            @RequestParam Integer directionSeq) {

        return scheduleRepository.findSchedulesForSearch(routeId, directionSeq);
    }
}
