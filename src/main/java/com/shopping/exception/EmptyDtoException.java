package com.shopping.exception;
public class EmptyDtoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyDtoException() {
		super("Dto is empty or null");
	}

}
