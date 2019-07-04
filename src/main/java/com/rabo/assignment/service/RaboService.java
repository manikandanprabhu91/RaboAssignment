package com.rabo.assignment.service;

import java.io.InputStream;

import com.rabo.assignment.domain.RaboRecordResponse;
import com.rabo.assignment.exptions.RaboException;

public interface RaboService {

	public RaboRecordResponse dataProcessor(InputStream inputStream, String fileType) throws RaboException;
	
}
