package com.carPooling.entity;

import java.time.LocalDateTime;

import com.carPooling.entity.enums.Payment;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String StartPoint;
	private String EndPoint;
	private LocalDateTime startTime;
	private Long totalAmount;
	@OneToOne
	private User driverDetails;
	
	@ManyToOne
	private User passeangerDetails;
	@Enumerated(EnumType.STRING)
	private Payment isPaid=Payment.UNPAID;
	
}
