package org.example.backendtonobil.repository;

import org.example.backendtonobil.entity.CarService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarServiceRepository extends JpaRepository<CarService, Long> {
    List<CarService> findByCarId(Long carId);
}
