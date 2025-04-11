package com.carPooling.excepations;

public class DriverAleadyPostedRideExcepation extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DriverAleadyPostedRideExcepation(String msg)
	{
		super(msg);
	}

}
