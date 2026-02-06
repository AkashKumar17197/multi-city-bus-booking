package com.busbooking.city_service.repository;

import com.busbooking.city_service.dto.CityResponse;
import com.busbooking.city_service.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

    Optional<CityEntity> findByCityCodeAndStatus(String cityCode, String status);

    List<CityEntity> findAllByStatus(String status);

    // Step 1 query to get fromCities and toCities
    @Query(
            value = "SELECT city_id FROM cities_c WHERE parent_city_id = :parentId " +
                    "UNION " +
                    "SELECT city_id FROM cities_c WHERE parent_city_id = " +
                    "(SELECT parent_city_id FROM cities_c WHERE city_id = :parentId)",
            nativeQuery = true
    )
    List<Long> findAllCitiesByParent(@Param("parentId") Long parentId);
}