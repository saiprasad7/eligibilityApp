package com.eligibility.benefit.util;

import javax.ws.rs.core.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseHandlingUtil {

private ResponseHandlingUtil(){
		
	}
	
	public static  ResponseEntity<Object> prepareResponse(Object object) {
	
		if(object instanceof ErrorResponse) {
			ErrorResponse error = (ErrorResponse) object;
			if(error.getErrorCode() == null) {
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				return new ResponseEntity<Object>(error,HttpStatus.valueOf(error.getErrorCode()));
			}
		}
		
		else if(null==object) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
					} 
		
		else {
			return ResponseEntity.ok(object);
		}
	}
}
