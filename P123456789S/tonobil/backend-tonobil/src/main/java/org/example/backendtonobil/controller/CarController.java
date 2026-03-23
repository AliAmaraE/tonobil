package org.example.backendtonobil.controller;

import org.example.backendtonobil.entity.Car;
import org.example.backendtonobil.entity.Owner;
import org.example.backendtonobil.repository.CarRepository;
import org.example.backendtonobil.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping
    public ResponseEntity<List<Car>> getCarsByOwner(@RequestParam Long ownerId) {
        return ResponseEntity.ok(carRepository.findByOwnerId(ownerId));
    }

    @PostMapping
    public ResponseEntity<?> addCar(@RequestParam Long ownerId, @RequestBody Car car) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if (owner.isPresent()) {
            car.setOwner(owner.get());
            Car savedCar = carRepository.save(car);
            return ResponseEntity.ok(savedCar);
        }
        return ResponseEntity.badRequest().body("Owner not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        return carRepository.findById(id).map(car -> {
            car.setBrand(carDetails.getBrand());
            car.setModel(carDetails.getModel());
            car.setYear(carDetails.getYear());
            car.setLicensePlate(carDetails.getLicensePlate());
            return ResponseEntity.ok(carRepository.save(car));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {
        return carRepository.findById(id).map(car -> {
            carRepository.delete(car);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
