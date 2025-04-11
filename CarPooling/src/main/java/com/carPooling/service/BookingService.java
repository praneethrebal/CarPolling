package com.carPooling.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
	private final BookingHistoryService historyService;
	private final UserService userservice;

	public BookingDTO bookRide(Long id) {
		String name=getAuthenticatedUsername();
		User passeanger=userService.findByUsername(name);
		if(passeanger == null){throw new UserNotLoginExcepation("Plese LogIn and get back ");}
		
		RideDetails ride=rideService.findById(id);
		if(ride == null){throw new RidesNotAvaliableExcepation("No Rides Avaliable");}
		
		Booking book=mapToBooking(ride,passeanger);
		bookingRepo.save(book);
		
		return dto(book);
	
	}

	public List<BookingDTO >findAllRides() {
		
		String username=getAuthUser();
		List<Booking> book= bookingRepo.findAllByUsername(username);
		if(book.isEmpty())
		{
			throw new RidesNotAvaliableExcepation("No Ride avaliable for you");
		}
		return book.stream().map(this::dto).collect(Collectors.toList());
	}
	public List<BookingDTO> getAllBookings() {
		String username=getAuthUser();
		List<Booking> book =bookingRepo.getAllUserBookings(username);
		return book.stream().map(this::dto).collect(Collectors.toList());
	}
	
	@Transactional
	public void conformRide(long id) {

		Booking accpect=bookingRepo.findById(id).orElse(null);
		RideDetails ride=accpect.getRideDetails();
		  if (ride == null) {
		        throw new RuntimeException("Ride details not found for this booking");
		    }
		bookingRepo.updateRideStatus(RideConformation.ACCEPTED, id);
		bookingRepo.rejectOtherBookings(ride.getId(),id);
		List<Booking> b=bookingRepo.gellAllRejected();
		historyService.rejectedBookings(b);
		bookingRepo.deleteRejected();
	}
	public void rejectRide(long id) {
		
		bookingRepo.updateRideStatus(RideConformation.REJECTED, id);
		Booking book=bookingRepo.findById(id).orElse(null);
		List<Booking> bookingList = new ArrayList<>();
		if (book != null) {
		    bookingList.add(book);
		}
		historyService.rejectedBookings(bookingList);
		bookingRepo.deleteRejected();
		

	}
	
	
	@Transactional
	public void completeRide(Long bookingId) {
	   
	    Booking booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RidesNotAvaliableExcepation("No rides Avaliable"));
	    RideDetails rideDetails = booking.getRideDetails();
	    User passenger1 =userservice .findById(booking.getPasseangerDetails().getId());
	    User driver1 = userservice.findById(rideDetails.getDriverDetails().getId());
	    historyService.saveHistory(booking,passenger1,driver1,rideDetails);
	    bookingRepo.deleteRecord(bookingId);
	    
	    rideService.delete(rideDetails.getId());
	    
	}
	


	
	
	//-----------------------------------------------------
	// MAPPINGS FUNCTIONS
	//-----------------------------------------------------

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

	private Booking mapToBooking(RideDetails ride, User passeanger) {
		Booking book =new Booking();
		book.setDate(LocalDate.now());
		book.setTime(LocalTime.now());
		book.setDriverId(ride.getDriverDetails().getId());
		book.setDriverName(ride.getDriverDetails().getName());
		book.setRideDetails(ride);
		book.setPasseangerDetails(passeanger);
		book.setRideStatus(RideConformation.PENDING);
		return book;
	}
	
	
	//-------------------------------------------
	// Get Authorize User
	//-------------------------------------------
	
	private String getAuthenticatedUsername() {
		UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}
	public String getAuthUser()
	{
		UserDetails details=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return details.getUsername();
	}

	






	










	






	


}
