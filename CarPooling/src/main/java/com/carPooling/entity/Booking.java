package com.carPooling.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.carPooling.entity.enums.RideConformation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private RideConformation rideStatus=RideConformation.PENDING;
	@NotNull
	private String driverName;
	@NotNull
	private Long driverId;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private RideDetails rideDetails;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private User passeangerDetails;
	private LocalTime time;
	private LocalDate date;

}
