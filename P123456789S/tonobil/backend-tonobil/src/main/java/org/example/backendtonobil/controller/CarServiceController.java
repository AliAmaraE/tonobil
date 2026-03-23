package org.example.backendtonobil.controller;

import org.example.backendtonobil.entity.CarService;
import org.example.backendtonobil.entity.Car;
import org.example.backendtonobil.repository.CarServiceRepository;
import org.example.backendtonobil.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class CarServiceController {

    @Autowired
    private CarServiceRepository carServiceRepository;

    @Autowired
    private CarRepository carRepository;

    @GetMapping
    public ResponseEntity<List<CarService>> getServicesByCar(@RequestParam Long carId) {
        return ResponseEntity.ok(carServiceRepository.findByCarId(carId));
    }

    @PostMapping
    public ResponseEntity<?> addService(@RequestParam Long carId, @RequestBody CarService service) {
        Optional<Car> car = carRepository.findById(carId);
        if (car.isPresent()) {
            service.setCar(car.get());
            CarService savedService = carServiceRepository.save(service);
            return ResponseEntity.ok(savedService);
        }
        return ResponseEntity.badRequest().body("Car not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarService> updateService(@PathVariable Long id, @RequestBody CarService serviceDetails) {
        return carServiceRepository.findById(id).map(service -> {
            service.setServiceType(serviceDetails.getServiceType());
            service.setDescription(serviceDetails.getDescription());
            service.setCost(serviceDetails.getCost());
            service.setKilometerAtService(serviceDetails.getKilometerAtService());
            service.setNextServiceKilometer(serviceDetails.getNextServiceKilometer());
            service.setServiceDate(serviceDetails.getServiceDate());
            service.setOilFilterChanged(serviceDetails.isOilFilterChanged());
            service.setFuelFilterChanged(serviceDetails.isFuelFilterChanged());
            service.setAirFilterChanged(serviceDetails.isAirFilterChanged());
            service.setAcAirFilterChanged(serviceDetails.isAcAirFilterChanged());
            return ResponseEntity.ok(carServiceRepository.save(service));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        return carServiceRepository.findById(id).map(service -> {
            carServiceRepository.delete(service);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
