package com.carPooling.dto;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.carPooling.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {
	@NotNull
	private LocalDate date;
	@NotNull
	private LocalTime time;
	@NotNull
	private String driverName;
	@NotNull
	private Long amount;
	@NotNull
	private String start_point;
	@NotNull
	private String end_Point;
	

}
