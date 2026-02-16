package com.shopping.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String resourceName;
	private final String fieldName;
	private final String fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {

		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));

		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}
}
