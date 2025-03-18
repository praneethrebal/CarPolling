package com.carPooling.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverRideDto {
	
	
	private Long id;
	@NotNull
	private String startPoint;
	@NotNull
	private String endPoint;
	@NotNull
	private LocalDateTime startTime;
	@NotNull
	private String  driverName;
	private String driverPhoneNo;
	@NotNull
	private Long totalAmountt;



}
