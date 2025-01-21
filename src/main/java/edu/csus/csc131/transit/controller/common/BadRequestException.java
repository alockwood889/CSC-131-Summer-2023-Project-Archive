package edu.csus.csc131.transit.controller.common;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 8119310450716704068L;

	public BadRequestException(String msg) {
		super(msg);
	}
}