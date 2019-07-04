package com.rabo.assignment.exptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RaboException extends RuntimeException {

	public RaboException(String exception) {
        super(exception);
    }
}
