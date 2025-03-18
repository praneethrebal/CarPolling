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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private RideConformation rideStatus=RideConformation.PENDING;
	
	@ManyToOne
	private RideDetails rideDetails;
	@ManyToOne
	private User passeangerDetails;
	private LocalTime time;
	private LocalDate date;
	

}
