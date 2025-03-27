package com.carPooling.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carPooling.entity.RideDetails;

import jakarta.transaction.Transactional;


@Repository
public interface RideDetailsRepo extends JpaRepository<RideDetails, Long>{
	
	@Query("select r from RideDetails r where r.StartPoint Like %:startPoint% and r.EndPoint Like %:endPoint%")
	List<RideDetails> searchride(String startPoint, String endPoint);
	
	@Modifying
	@Query("delete from RideDetails r where r.id=:rideDetails")
	void deleteRecord(Long rideDetails);
	
//	@Modifying
//	@Transactional
//	@Query("DELETE FROM Booking b WHERE b.rideDetails.id = :rideId")
//	void deleteBookingsByRideId(Long rideId);
//
//	@Modifying
//	@Transactional
//	@Query("DELETE FROM RideDetails r WHERE r.id = :rideId")
//	void deleteRideById(Long rideId);

	
	/*
	 * StartPoint;
	private String EndPoint;
*/
}
