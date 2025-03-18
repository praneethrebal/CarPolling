package com.carPooling.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carPooling.entity.RideDetails;


@Repository
public interface RideDetailsRepo extends JpaRepository<RideDetails, Long>{
	
	@Query("select r from RideDetails r where r.StartPoint Like %:startPoint% and r.EndPoint Like %:endPoint%")
	List<RideDetails> searchride(String startPoint, String endPoint);
	
	/*
	 * StartPoint;
	private String EndPoint;
*/
}
