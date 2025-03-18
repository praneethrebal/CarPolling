package com.carPooling.excepations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class UserNotLoginExcepation extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserNotLoginExcepation(String msg)
	{
		super(msg);
	}

}
