package com.carPooling.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.carPooling.dto.HistoryDto;
import com.carPooling.entity.Booking;
import com.carPooling.entity.BookingHistory;
import com.carPooling.entity.RideDetails;
import com.carPooling.entity.User;
import com.carPooling.entity.enums.RideConformation;
import com.carPooling.repo.BookingHistoryRepo;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class BookingHistoryService {
	private final BookingHistoryRepo bookingHistoryRepo;
	private final UserService userService;

	public void saveHistory(Booking booking,User passenger, User driver,RideDetails rideDetails) {
		BookingHistory history = new BookingHistory();
	    history.setRideStatus(RideConformation.COMPLETED);
	    history.setStartPoint(rideDetails.getStartPoint());
	    history.setEndPoint(rideDetails.getEndPoint());
	    history.setTime(booking.getTime());
	    history.setDate(booking.getDate());
	    history.setTotalAmount(rideDetails.getTotalAmount());
	    history.setDriverDetails(driver);
	    history.setPassengerDetails(passenger);

	    // Save history first
	    bookingHistoryRepo.save(history);
		
		
	}

	public List<HistoryDto> getHistory() {
		UserDetails details=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String Username=details.getUsername();
		
		User userDetailsObj=userService.findByUsername(Username);
		
		List<BookingHistory> his=bookingHistoryRepo.findByPasseangerId(userDetailsObj.getId());
		
		return his.stream().map(this::historyDto).collect(Collectors.toList());
	}

	private HistoryDto historyDto(BookingHistory bookinghistory1) {
		HistoryDto dto=new HistoryDto();
		dto.setAmount(bookinghistory1.getTotalAmount());
		dto.setDate(bookinghistory1.getDate());
		dto.setDriverName(bookinghistory1.getDriverDetails().getName());
		dto.setEnd_Point(bookinghistory1.getEndPoint());
		dto.setStart_point(bookinghistory1.getStartPoint());
		dto.setTime(bookinghistory1.getTime());
		return dto;
	}


}
