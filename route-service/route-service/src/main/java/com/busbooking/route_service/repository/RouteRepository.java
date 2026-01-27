package com.busbooking.route_service.repository;

import com.busbooking.route_service.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<RouteEntity, Long> {

    List<RouteEntity> findByStatus(String status);
}