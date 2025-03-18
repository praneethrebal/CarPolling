package com.carPooling.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.carPooling.dto.BookingConformationDTO;
import com.carPooling.dto.BookingDTO;
import com.carPooling.entity.Booking;
import com.carPooling.entity.RideDetails;
import com.carPooling.entity.User;
import com.carPooling.entity.enums.RideConformation;
import com.carPooling.excepations.RidesNotAvaliableExcepation;
import com.carPooling.excepations.UserNotLoginExcepation;
import com.carPooling.repo.BookingRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class BookingService {
	private final BookingRepo bookingRepo;
	private final RideService rideService;
	private final UserService userService;
	

	public BookingDTO bookRide(Long id) {
		
		
		String name=getAuthenticatedUsername();
		User passeanger=userService.findByUsername(name);
		if(passeanger == null){throw new UserNotLoginExcepation("Plese LogIn and get back ");}
		
		RideDetails ride=rideService.findById(id);
		if(ride == null){throw new RidesNotAvaliableExcepation("No Rides Avaliable");}
		
		Booking book=new Booking();
		book.setDate(LocalDate.now());
		book.setTime(LocalTime.now());
		book.setRideDetails(ride);
		book.setPasseangerDetails(passeanger);
		book.setRideStatus(RideConformation.PENDING);
		bookingRepo.save(book);
		return dto(book);
	
	}
	

	public List<BookingDTO >findAllRides() {
		UserDetails details=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=details.getUsername();
		List<Booking> book= bookingRepo.findAllByUsername(username);
		return book.stream().map(this::dto).collect(Collectors.toList());
	}
	
	@Transactional
	public void conformRide(BookingConformationDTO rideStatus,Long id) {
		RideConformation status=rideStatus.getRideStatus();
		bookingRepo.updateRideStatus(status,id);
		if(status == RideConformation.ACCEPTED )
		{
		 Booking book=bookingRepo.findById(id).orElseThrow(()-> new RidesNotAvaliableExcepation("Booking Not Found"));
		 rideService.updatePasseangerDetails(book);
		 }
	}
	
	
	private String getAuthenticatedUsername() {
		UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}
	private BookingDTO dto(Booking book)
	{
		BookingDTO dto=new BookingDTO();
		dto.setId(book.getId());
		dto.setStartPoint(book.getRideDetails().getStartPoint());
		dto.setEndPoint(book.getRideDetails().getEndPoint());
		dto.setDriverName(book.getRideDetails().getDriverDetails().getName());
		dto.setDrivePhone_No(book.getRideDetails().getDriverDetails().getPhoneNo());
		dto.setTotalAmount(book.getRideDetails().getTotalAmount());
		dto.setRideStatus(book.getRideStatus().name());
		dto.setPasseangerName(book.getPasseangerDetails().getName());
		dto.setPasseangerPhone_NO(book.getPasseangerDetails().getPhoneNo());
		dto.setBookingDate(book.getDate());
		dto.setBookingTime(LocalTime.now());
		
		return dto;
	}
	



	


}
