package com.carPooling.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carPooling.entity.Booking;
import com.carPooling.entity.enums.RideConformation;

import jakarta.transaction.Transactional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long>{
	
	@Query("select b from Booking b where b.rideDetails.driverDetails.username =:username")
	List<Booking> findAllByUsername(String username);
	
	@Query("select b from Booking b where b.passeangerDetails.username =:username")
	List<Booking> getAllUserBookings(String username);
	
	@Modifying
	@Transactional
	@Query("update Booking b set b.rideStatus=:status where b.id=:id")
	void updateRideStatus(RideConformation status, Long id);

	@Query("select b from Booking b where b.passeangerDetails.id=:id")
	List<Booking> findAllById(Long id);

	@Modifying
	@Query("UPDATE Booking b SET b.rideDetails = NULL WHERE b.rideDetails.id = :id")
	void clearRideDetails(Long id);
	
	@Modifying
	@Query("delete from Booking b where b.id=:booking")
	void deleteRecord(Long booking);

	
	@Modifying
	@Transactional
	@Query("UPDATE Booking b SET b.rideStatus = 'REJECTED', b.rideDetails = NULL WHERE b.rideDetails.id = :rideId AND b.id <> :acceptedBookingId")
	void rejectOtherBookings(@Param("rideId") Long rideId, @Param("acceptedBookingId") Long acceptedBookingId);
	
	@Query("select  b from Booking b where b.rideStatus = 'REJECTED'")
	List<Booking> gellAllRejected();
   
	@Transactional
	@Modifying
	@Query("delete from Booking b where b.rideStatus = 'REJECTED'")
	void deleteRejected();	
	

}
