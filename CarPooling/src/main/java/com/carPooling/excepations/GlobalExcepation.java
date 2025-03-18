package com.carPooling.excepations;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.carPooling.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExcepation {
	
	@ExceptionHandler(DriverNotFoundExcepation.class)
	public ResponseEntity<ErrorResponseDTO> handleDriveNotFound(DriverNotFoundExcepation ex,WebRequest req)
	{
		
		ErrorResponseDTO err=new ErrorResponseDTO(req.getDescription(false), HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException excepation,WebRequest req)
	{
		ErrorResponseDTO err=new ErrorResponseDTO(req.getDescription(false), HttpStatus.NOT_FOUND, excepation.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RidesNotAvaliableExcepation.class)
	public ResponseEntity<ErrorResponseDTO> handleRidesNotAvaliableExcepation(RidesNotAvaliableExcepation excepation,WebRequest req)
	{
		ErrorResponseDTO error=new ErrorResponseDTO(req.getDescription(false), HttpStatus.NOT_FOUND, excepation.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotLoginExcepation.class)
	public ResponseEntity<ErrorResponseDTO> handleUserNotLoginExcepation(UserNotLoginExcepation excapation,WebRequest req)
	{
		ErrorResponseDTO error= new ErrorResponseDTO(req.getDescription(false), HttpStatus.NOT_ACCEPTABLE, excapation.getMessage(), LocalDateTime.now());
		return new  ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE) ;
	}

}
