package com.busbooking.city_service.service;

import com.busbooking.city_service.dto.CityResponse;
import com.busbooking.city_service.entity.CityEntity;

import java.util.List;

public interface CityService {

    CityEntity createCity(CityEntity city);

    CityEntity updateCity(Long cityId, CityEntity city);

    CityEntity getCityById(Long cityId);

    List<CityEntity> getAllCities();

    void deleteCity(Long cityId);

    CityResponse getCitiesForRoute(Long fromCityId, Long toCityId);
}