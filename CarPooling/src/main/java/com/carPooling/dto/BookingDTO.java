package com.carPooling.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class BookingDTO {
	private Long id;
	private String startPoint;
	private String endPoint;
	private LocalTime bookingTime;
	private LocalDate bookingDate;
	
	private String driverName;
	private String drivePhone_No;
	private Long totalAmount;
	private String rideStatus;
	
	private String passeangerName;
	private String passeangerPhone_NO;
	
	

}
