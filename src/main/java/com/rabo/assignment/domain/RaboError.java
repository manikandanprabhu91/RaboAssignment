package com.rabo.assignment.domain;

import java.util.List;

public class RaboError {
	
	public RaboError(List<String> details, String errorMessage) {
		super();
        this.errorMessage = errorMessage;
        this.details = details;
	}
	
	private String errorMessage;
	private List<String> details;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	

}
