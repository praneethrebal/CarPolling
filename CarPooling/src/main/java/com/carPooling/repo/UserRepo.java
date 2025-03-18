package com.carPooling.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carPooling.entity.User;
@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	@Query("select u from User u where u.username=:username")
	 User findByUsername(String username) ;
	
	@Query("select u.role from User u where u.username=:username")
	String findRole(String username);



}
