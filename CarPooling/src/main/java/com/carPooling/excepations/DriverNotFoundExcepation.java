package com.carPooling.excepations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DriverNotFoundExcepation extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DriverNotFoundExcepation(String msg)
	{
		super(msg);
	}

}
