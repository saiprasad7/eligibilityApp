package com.eligibility.benefit.util;

public class ExceptionHandlingUtil {

    private ExceptionHandlingUtil() {
        throw new AssertionError("Utility class ExceptionHandlingUtil should never be initialized");
    }

    public static ErrorResponse returnErrorObject(String logText, Integer errorCode) {
        return new ErrorResponse(logText, errorCode);
    }
}
