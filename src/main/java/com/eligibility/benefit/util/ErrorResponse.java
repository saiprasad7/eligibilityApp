package com.eligibility.benefit.util;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {

	 @NonNull
	private String errorMessage;
	
	private Object exceptionObj;
	 @NonNull
	private Integer errorCode;
	
}
