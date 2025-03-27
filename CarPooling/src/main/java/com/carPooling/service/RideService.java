package com.carPooling.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carPooling.dto.DriverRideDto;
import com.carPooling.dto.SerchRide;
import com.carPooling.entity.RideDetails;
import com.carPooling.entity.User;
import com.carPooling.excepations.DriverNotFoundExcepation;
import com.carPooling.excepations.RidesNotAvaliableExcepation;
import com.carPooling.repo.RideDetailsRepo;
import com.carPooling.repo.UserRepo;

import jakarta.transaction.Transactional;


@Service
public class RideService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RideDetailsRepo rideDetailsRepo;
	@Autowired
	private BookingService bookingService;

	

	public void newRide(DriverRideDto rideDetails) {
		RideDetails ride= new RideDetails();
		
		User driverDet=userRepo.findByUsername(rideDetails.getDriverName());
		
		if(driverDet == null)
		{
			throw new DriverNotFoundExcepation("Driver Not Found");
		}
		
		ride.setStartPoint(rideDetails.getStartPoint());
		ride.setEndPoint(rideDetails.getEndPoint());
		ride.setStartTime(rideDetails.getStartTime());
		ride.setDriverDetails(driverDet);
		ride.setTotalAmount(rideDetails.getTotalAmountt());
		rideDetailsRepo.save(ride);		
	}
	public List<DriverRideDto >searchRide(SerchRide searhRide) {
		List<RideDetails> rides= rideDetailsRepo.searchride(searhRide.getStartPoint(),searhRide.getEndPoint());
		
		if(rides.isEmpty())
		{
			throw new RidesNotAvaliableExcepation("No Rides Found in this Route");
		}
		
		List<DriverRideDto> serchDetails=new ArrayList<DriverRideDto>();

	    for (RideDetails ride : rides) {
	        DriverRideDto dto = new DriverRideDto();
	        dto.setId(ride.getId());
	        dto.setDriverPhoneNo(ride.getDriverDetails().getPhoneNo());
	        dto.setDriverName(ride.getDriverDetails().getUsername());
	        dto.setStartPoint(ride.getStartPoint());
	        dto.setEndPoint(ride.getEndPoint());
	        dto.setStartTime(ride.getStartTime());
	        dto.setTotalAmountt(ride.getTotalAmount());
	        serchDetails.add(dto);
	    }
		return serchDetails;
	}
	
	public RideDetails findById(Long id) {
		
		return rideDetailsRepo.findById(id).orElseThrow(()->new RidesNotAvaliableExcepation("No Rides Avaliable"));
	}
	
	@Transactional
	public void rideComplect(Long id) {
		bookingService.completeRide(id);
		
	}
	public void delete(Long rideDetails) {
		// TODO Auto-generated method stb
		rideDetailsRepo.deleteRecord(rideDetails);
//		rideDetailsRepo.deleteBookingsByRideId(rideDetails);
//
//		rideDetailsRepo.deleteRideById(rideDetails);

		
	}
	


}
