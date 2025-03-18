package com.carPooling.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carPooling.dto.BookingDTO;
import com.carPooling.dto.DriverRideDto;
import com.carPooling.dto.SerchRide;
import com.carPooling.service.BookingService;
import com.carPooling.service.RideService;



@RequestMapping("/passeanger")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PasseangerController {
	
	@Autowired
	private  RideService rideService;
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/search")
	public ResponseEntity<List<DriverRideDto> >  searchRide(@RequestBody SerchRide searhRide)
	{
		List<DriverRideDto> rides=rideService.searchRide(searhRide);
		
		return ResponseEntity.status(HttpStatus.OK).body(rides) ;
	}
	
	@PostMapping("/book/{id}")
	public ResponseEntity<BookingDTO> bookRide(@PathVariable Long id)
	{
		BookingDTO bookings=bookingService.bookRide(id);
		return ResponseEntity.status(HttpStatus.OK).body(bookings);
	}

}
