package com.rabo.assignment.domain;

import java.util.List;

public class RaboRecordResponse {
	
	private List<RaboResponse> raboResponse;
	private String message;


	public List<RaboResponse> getRaboResponse() {
		return raboResponse;
	}

	public void setRaboResponse(List<RaboResponse> raboResponse) {
		this.raboResponse = raboResponse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
