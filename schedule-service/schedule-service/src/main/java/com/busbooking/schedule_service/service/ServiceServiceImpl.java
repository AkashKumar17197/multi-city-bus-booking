package com.busbooking.schedule_service.service;

import com.busbooking.schedule_service.dto.RestStopDTO;
import com.busbooking.schedule_service.dto.ScheduleDTO;
import com.busbooking.schedule_service.dto.ServiceRequestDTO;
import com.busbooking.schedule_service.dto.ServiceResponseDTO;
import com.busbooking.schedule_service.entity.ScheduleRestStopEntity;
import com.busbooking.schedule_service.entity.ServiceEntity;
import com.busbooking.schedule_service.entity.ServiceScheduleEntity;
import com.busbooking.schedule_service.repository.ScheduleRestStopRepository;
import com.busbooking.schedule_service.repository.ServiceRepository;
import com.busbooking.schedule_service.repository.ServiceScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceScheduleRepository scheduleRepository;
    private final ScheduleRestStopRepository restStopRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository,
                              ServiceScheduleRepository scheduleRepository,
                              ScheduleRestStopRepository restStopRepository) {
        this.serviceRepository = serviceRepository;
        this.scheduleRepository = scheduleRepository;
        this.restStopRepository = restStopRepository;
    }

    // ================= CREATE =================
    @Override
    public ServiceResponseDTO createService(ServiceRequestDTO request) {

        // 1️⃣ Save Service
        ServiceEntity service = new ServiceEntity();
        service.setRouteId(request.getRouteId());
        service.setDriverId1(request.getDriverId1());
        service.setDriverId2(request.getDriverId2());
        service.setDriverId3(request.getDriverId3());
        service.setDriverId4(request.getDriverId4());
        service.setStatus(request.getStatus());
        service.setCreatedBy(request.getCreatedBy());
        service.setCreatedDate(LocalDateTime.now());

        service = serviceRepository.save(service);

        // 2️⃣ Save both schedules
        ServiceScheduleEntity ongoing =
                saveSchedule(service.getServiceId(), request.getScheduleOngoing(), 1);

        ServiceScheduleEntity returning =
                saveSchedule(service.getServiceId(), request.getScheduleReturn(), 2);

        // 3️⃣ Response
        ServiceResponseDTO response = mapService(service);
        response.setScheduleOngoing(mapSchedule(ongoing));
        response.setScheduleReturn(mapSchedule(returning));

        return response;
    }

    // ================= UPDATE =================
    @Override
    public ServiceResponseDTO updateService(ServiceRequestDTO request) {

        ServiceEntity service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setDriverId1(request.getDriverId1());
        service.setDriverId2(request.getDriverId2());
        service.setDriverId3(request.getDriverId3());
        service.setDriverId4(request.getDriverId4());
        service.setStatus(request.getStatus());
        service.setUpdatedBy(request.getUpdatedBy());
        service.setUpdatedDate(LocalDateTime.now());

        serviceRepository.save(service);

        // Update schedules (delete old rest stops → insert new)
        ServiceScheduleEntity ongoing =
                updateSchedule(service.getServiceId(), request.getScheduleOngoing(), 1);

        ServiceScheduleEntity returning =
                updateSchedule(service.getServiceId(), request.getScheduleReturn(), 2);

        ServiceResponseDTO response = mapService(service);
        response.setScheduleOngoing(mapSchedule(ongoing));
        response.setScheduleReturn(mapSchedule(returning));

        return response;
    }

    // ================= FETCH =================
    @Override
    public ServiceResponseDTO getService(Long serviceId) {

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        ServiceScheduleEntity ongoing =
                scheduleRepository.findByServiceIdAndDirectionSeq(serviceId, 1)
                        .orElseThrow(() -> new RuntimeException("Ongoing schedule not found"));

        ServiceScheduleEntity returning =
                scheduleRepository.findByServiceIdAndDirectionSeq(serviceId, 2)
                        .orElseThrow(() -> new RuntimeException("Return schedule not found"));

        ServiceResponseDTO response = mapService(service);
        response.setScheduleOngoing(mapSchedule(ongoing));
        response.setScheduleReturn(mapSchedule(returning));

        return response;
    }

    // ================= DELETE =================
    @Override
    public void deleteService(Long serviceId) {

        List<ServiceScheduleEntity> schedules =
                scheduleRepository.findByServiceId(serviceId);

        for (ServiceScheduleEntity schedule : schedules) {
            restStopRepository.deleteByScheduleId(schedule.getScheduleId());
        }

        scheduleRepository.deleteByServiceId(serviceId);

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setStatus("DELETED");
        service.setUpdatedDate(LocalDateTime.now());

        serviceRepository.save(service);
    }

    // ================= HELPERS =================

    private ServiceScheduleEntity saveSchedule(
            Long serviceId, ScheduleDTO dto, int directionSeq) {

        ServiceScheduleEntity schedule = new ServiceScheduleEntity();
        schedule.setServiceId(serviceId);
        schedule.setScheduleName(dto.getScheduleName());
        schedule.setDirectionSeq(directionSeq);
        schedule.setDepartureTime(dto.getDepartureTime());
        schedule.setRunningStatus(dto.getRunningStatus());

        schedule = scheduleRepository.save(schedule);

        saveRestStops(schedule.getScheduleId(), dto.getRestStops());

        return schedule;
    }

    private ServiceScheduleEntity updateSchedule(
            Long serviceId, ScheduleDTO dto, int directionSeq) {

        ServiceScheduleEntity schedule =
                scheduleRepository.findByServiceIdAndDirectionSeq(serviceId, directionSeq)
                        .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setScheduleName(dto.getScheduleName());
        schedule.setDepartureTime(dto.getDepartureTime());
        schedule.setRunningStatus(dto.getRunningStatus());

        scheduleRepository.save(schedule);

        restStopRepository.deleteByScheduleId(schedule.getScheduleId());
        saveRestStops(schedule.getScheduleId(), dto.getRestStops());

        return schedule;
    }

    private void saveRestStops(Long scheduleId, List<RestStopDTO> restStops) {

        if (restStops == null) return;

        for (RestStopDTO dto : restStops) {
            ScheduleRestStopEntity stop = new ScheduleRestStopEntity();
            stop.setScheduleId(scheduleId);
            stop.setCityStopId(dto.getCityStopId());
            stop.setLocationName(dto.getLocationName());
            stop.setMapLink(dto.getMapLink());
            stop.setMinutes(dto.getMinutes());
            restStopRepository.save(stop);
        }
    }

    private ServiceResponseDTO mapService(ServiceEntity service) {

        ServiceResponseDTO dto = new ServiceResponseDTO();
        dto.setServiceId(service.getServiceId());
        dto.setRouteId(service.getRouteId());
        dto.setDriverId1(service.getDriverId1());
        dto.setDriverId2(service.getDriverId2());
        dto.setDriverId3(service.getDriverId3());
        dto.setDriverId4(service.getDriverId4());
        dto.setStatus(service.getStatus());
        return dto;
    }

    private ScheduleDTO mapSchedule(ServiceScheduleEntity schedule) {

        ScheduleDTO dto = new ScheduleDTO();
        dto.setScheduleId(schedule.getScheduleId());
        dto.setScheduleName(schedule.getScheduleName());
        dto.setDirectionSeq(schedule.getDirectionSeq());
        dto.setDepartureTime(schedule.getDepartureTime());
        dto.setRunningStatus(schedule.getRunningStatus());

        List<ScheduleRestStopEntity> stops =
                restStopRepository.findByScheduleId(schedule.getScheduleId());

        List<RestStopDTO> restStopDTOs = new ArrayList<>();
        for (ScheduleRestStopEntity stop : stops) {
            RestStopDTO r = new RestStopDTO();
            r.setSrsId(stop.getSrsId());
            r.setCityStopId(stop.getCityStopId());
            r.setLocationName(stop.getLocationName());
            r.setMapLink(stop.getMapLink());
            r.setMinutes(stop.getMinutes());
            restStopDTOs.add(r);
        }

        dto.setRestStops(restStopDTOs);
        return dto;
    }
}
