package com.carPooling.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carPooling.entity.BookingHistory;

public interface BookingHistoryRepo extends JpaRepository<BookingHistory, Long> {

	@Query("select h from BookingHistory h where h.passengerDetails.id=:id")
	 List<BookingHistory >findByPasseangerId(Long id) ;
}
