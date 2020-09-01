package com.eligibility.benefit.util;

public class ExceptionHandlingUtil {

	
	public static ErrorResponse returnErrorObject(String logText, Integer errorCode) { 
		return new ErrorResponse(logText , errorCode);
	}
}
