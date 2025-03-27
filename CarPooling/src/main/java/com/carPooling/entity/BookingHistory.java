package com.carPooling.entity;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.carPooling.entity.enums.RideConformation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RideConformation rideStatus;

    private String startPoint;
    private String endPoint;
    private LocalTime time;
    private LocalDate date;
    private Long totalAmount;
    @ManyToOne
//    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private User driverDetails;
//    @ManyToMany(cascade = CascadeType.MERGE)
//   
//    private List<User> driverDetails;
//    @ManyToMany(cascade = CascadeType.MERGE)
//   
//    private List<User> passengerDetails;
    @ManyToOne
//    @JoinColumn(name = "passenger_id", referencedColumnName = "id", nullable = false)
    private  User passengerDetails;
}

