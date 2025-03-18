package com.carPooling.dto;

import com.carPooling.entity.enums.RideConformation;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BookingConformationDTO {
	@JsonProperty("rideStatus")
	private RideConformation rideStatus;

}
