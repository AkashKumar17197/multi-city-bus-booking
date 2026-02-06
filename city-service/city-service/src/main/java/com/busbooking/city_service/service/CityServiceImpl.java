package com.busbooking.city_service.service;

import com.busbooking.city_service.dto.CityResponse;
import com.busbooking.city_service.entity.CityEntity;
import com.busbooking.city_service.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    // ✅ Manual constructor (Spring will inject automatically)
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CityEntity createCity(CityEntity city) {
        return cityRepository.save(city);
    }

    @Override
    public CityEntity updateCity(Long cityId, CityEntity city) {
        CityEntity existing = getCityById(cityId);

        existing.setCityName(city.getCityName());
        existing.setCityCode(city.getCityCode());
        existing.setParentCityId(city.getParentCityId());
        existing.setDirectionFromState(city.getDirectionFromState());
        existing.setDirectionFromCountry(city.getDirectionFromCountry());
        existing.setUpdatedBy(city.getUpdatedBy());

        return cityRepository.save(existing);
    }

    @Override
    public CityEntity getCityById(Long cityId) {
        return cityRepository.findById(cityId)
                .filter(c -> "LIVE".equals(c.getStatus()))
                .orElseThrow(() -> new RuntimeException("City not found"));
    }

    @Override
    public List<CityEntity> getAllCities() {
        return cityRepository.findAllByStatus("LIVE");
    }

    @Override
    public void deleteCity(Long cityId) {
        CityEntity city = getCityById(cityId);
        city.setStatus("DELETED");
        cityRepository.save(city);
    }

    public CityResponse getCitiesForRoute(Long fromCityId, Long toCityId) {
        List<Long> fromCities = cityRepository.findAllCitiesByParent(fromCityId);
        List<Long> toCities = cityRepository.findAllCitiesByParent(toCityId);

        return new CityResponse(fromCities, toCities);
    }
}