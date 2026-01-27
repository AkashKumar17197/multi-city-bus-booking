package com.busbooking.city_service;

import com.busbooking.city_service.entity.CityEntity;
import com.busbooking.city_service.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    // ✅ Manual constructor (constructor injection)
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<CityEntity> createCity(@RequestBody CityEntity city) {
        return ResponseEntity.ok(cityService.createCity(city));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityEntity> getCity(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @GetMapping
    public ResponseEntity<List<CityEntity>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityEntity> updateCity(
            @PathVariable Long id,
            @RequestBody CityEntity city) {
        return ResponseEntity.ok(cityService.updateCity(id, city));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
