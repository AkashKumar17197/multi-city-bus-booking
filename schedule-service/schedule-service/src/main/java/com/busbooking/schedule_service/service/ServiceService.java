package com.busbooking.schedule_service.service;

import com.busbooking.schedule_service.dto.ServiceRequestDTO;
import com.busbooking.schedule_service.dto.ServiceResponseDTO;

public interface ServiceService {

    ServiceResponseDTO createService(ServiceRequestDTO request);

    ServiceResponseDTO updateService(ServiceRequestDTO request);

    ServiceResponseDTO getService(Long serviceId);

    void deleteService(Long serviceId);
}
