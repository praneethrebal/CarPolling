package com.carPooling.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.carPooling.entity.Booking;
import com.carPooling.entity.enums.RideConformation;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long>{
	
	@Query("select b from Booking b where b.rideDetails.driverDetails.username =:username")
	List<Booking> findAllByUsername(String username);
	
	@Modifying
	@Transactional
	@Query("update Booking b set b.rideStatus=:status where b.id=:id")
	void updateRideStatus(RideConformation status, Long id);
	
//	

}
