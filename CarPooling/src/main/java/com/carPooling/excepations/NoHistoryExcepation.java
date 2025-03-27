package com.carPooling.excepations;

public class NoHistoryExcepation extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoHistoryExcepation(String msg)
	{
		super(msg);
	}
	

}
