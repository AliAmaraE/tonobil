package org.example.backendtonobil.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Car> cars;
}