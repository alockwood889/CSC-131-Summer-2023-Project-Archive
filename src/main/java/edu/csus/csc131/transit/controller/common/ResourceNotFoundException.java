package edu.csus.csc131.transit.controller.common;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	
	public ResourceNotFoundException(String resourceName, String id) {
		super(String.format("%s with id %s not found",resourceName, id));
	}

}
