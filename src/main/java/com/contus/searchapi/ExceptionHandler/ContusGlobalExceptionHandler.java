package com.contus.searchapi.ExceptionHandler;

import org.json.simple.JSONObject;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContusGlobalExceptionHandler {

	@SuppressWarnings("unchecked")
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
		JSONObject exceptionJson = new JSONObject();
		exceptionJson.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
		exceptionJson.put("isError", true);
		exceptionJson.put("exceptionMessage", ex.getMessage());
		exceptionJson.put("message", "Null Pointer Exception During process");
		return new ResponseEntity<>(exceptionJson, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<Object> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
		JSONObject exceptionJson = new JSONObject();
		exceptionJson.put("statusCode", HttpStatus.BAD_REQUEST);
		exceptionJson.put("isError", true);
		exceptionJson.put("exceptionMessage", ex.getMessage());
		exceptionJson.put("message", "Some thing went to wrong");
		return new ResponseEntity<>(exceptionJson, HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(ContusException.class)
	public ResponseEntity<Object> handleContusException(ContusException ex) {
		JSONObject exceptionJson = new JSONObject();
		exceptionJson.put("statusCode", HttpStatus.BAD_REQUEST);
		exceptionJson.put("isError", true);
		exceptionJson.put("exceptionMessage", ex.getMessage());
		exceptionJson.put("message", ex.getErrorMessage());
		return new ResponseEntity<>(exceptionJson, HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleTransientPropertyValueException(Exception ex) {
		JSONObject exceptionJson = new JSONObject();
		exceptionJson.put("statusCode", HttpStatus.BAD_REQUEST);
		exceptionJson.put("isError", true);
		exceptionJson.put("exceptionMessage", ex.getMessage());
		exceptionJson.put("message", "Error Occured , Kindly contact Support Team");
		return new ResponseEntity<>(exceptionJson, HttpStatus.BAD_REQUEST);
	}

}
