package com.carPooling.entity;


import java.time.LocalDate;
import java.time.LocalTime;

import com.carPooling.entity.enums.RideConformation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private User driverDetails;
    @ManyToOne
    private  User passengerDetails;
}

