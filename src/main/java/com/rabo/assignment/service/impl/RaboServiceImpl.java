package com.rabo.assignment.service.impl;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabo.assignment.domain.RaboRecordResponse;
import com.rabo.assignment.exptions.RaboException;
import com.rabo.assignment.service.RaboService;
import com.rabo.assignment.utils.RaboUtil;

@Service
public class RaboServiceImpl implements RaboService{
	
	private static final Logger logger = LogManager.getLogger(RaboServiceImpl.class);
	
	@Autowired
	RaboUtil raboUtil;
	
	public RaboRecordResponse dataProcessor(InputStream inputStream, String fileType) throws RaboException {
		logger.info(":::::::::: Statrts Rabo Service dataProcessor method :::::::::::::::::");
		RaboRecordResponse recordResponse = raboUtil.loadAndValidateData(inputStream, fileType);
		logger.info(":::::::::: End Rabo Service dataProcessor method :::::::::::::::::");
		return recordResponse;
	}

}
