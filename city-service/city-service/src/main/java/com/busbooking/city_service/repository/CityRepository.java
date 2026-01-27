package com.busbooking.city_service.repository;

import com.busbooking.city_service.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

    Optional<CityEntity> findByCityCodeAndStatus(String cityCode, String status);

    List<CityEntity> findAllByStatus(String status);
}