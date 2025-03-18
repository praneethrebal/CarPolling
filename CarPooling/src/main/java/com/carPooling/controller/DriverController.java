package com.carPooling.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carPooling.dto.BookingConformationDTO;
import com.carPooling.dto.BookingDTO;
import com.carPooling.dto.DriverRideDto;
import com.carPooling.dto.ResponseDTO;
import com.carPooling.service.BookingService;
import com.carPooling.service.RideService;

import lombok.RequiredArgsConstructor;


@RequestMapping("/driver")
@RestController
@RequiredArgsConstructor
@CrossOrigin()
public class DriverController {
	private final RideService rideService;
	private final BookingService bookingService;
	
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDTO> postRide(@RequestBody DriverRideDto driverDetails)
	{
		rideService.newRide(driverDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body( new ResponseDTO(HttpStatus.CREATED,"Ride Posted Sucessfully"));
	}
	
	
	@GetMapping("findAllrides")
	public ResponseEntity<List< BookingDTO>> findAllRides()
	{
		List<BookingDTO> bookingDTO=  bookingService.findAllRides();
		return ResponseEntity.status(HttpStatus.OK).body(bookingDTO );
	}
	@PutMapping("/conform-ride/{id}")
	public ResponseEntity<ResponseDTO> rideConformation(@RequestBody BookingConformationDTO rideStatus,@PathVariable Long id)
	{
		bookingService.conformRide(rideStatus,id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.CREATED,"Ride Conformed"));
		
	}
	
	@DeleteMapping("/ride-complected/{id}")
	public ResponseEntity<ResponseDTO> rideComplected(@RequestBody BookingConformationDTO rideStatus,@PathVariable Long id)
	{
		rideConformation(rideStatus, id);
		rideService.deleteRide(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"Ride Complected"));
	}
	
	


}
