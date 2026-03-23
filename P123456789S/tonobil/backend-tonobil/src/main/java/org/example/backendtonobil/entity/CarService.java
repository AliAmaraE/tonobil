package org.example.backendtonobil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceType;
    private String description;
    private double cost;
    private int kilometerAtService;
    private int nextServiceKilometer;
    private LocalDate serviceDate;

    private boolean oilFilterChanged;
    private boolean fuelFilterChanged;
    private boolean airFilterChanged;
    private boolean acAirFilterChanged;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}