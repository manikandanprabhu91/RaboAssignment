package com.rabo.assignment.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabo.assignment.constant.RaboConstatnt;
import com.rabo.assignment.domain.RaboRecordResponse;
import com.rabo.assignment.exptions.RaboException;
import com.rabo.assignment.utils.RaboUtil;

@RestController
@RequestMapping(value = "/api/repo/assignment/v1/auth")
public class RaboController {
	
	private static final Logger logger = LogManager.getLogger(RaboController.class);
	
	@Autowired
	RaboUtil reboUtil;

	@PostMapping("/rebo-file-upload")
	public ResponseEntity<?> uploadCscOrXmlFile(@RequestParam("file") MultipartFile file, 
			HttpServletResponse response) throws IOException {
		
		if (logger.isDebugEnabled()) {
            logger.debug("::::::Start csvfileUpload method::::::");
        }
		
		String fileType = RaboConstatnt.DOUBLE_QUOTES;
		String[] extensionArray = file.getOriginalFilename().split(RaboConstatnt.DOUBLE_SLASH);
		String extension = extensionArray.length > 0 ? extensionArray[1] : null;
		
		logger.debug("::::::csvfileUpload method File extension checking::::::"+extension);
		
		// Validate INput FIle Format
		if (file.isEmpty() || RaboConstatnt.XML.equalsIgnoreCase(extension)) {
			fileType = RaboConstatnt.XML;
		} else if (file.isEmpty() || RaboConstatnt.CSV.equalsIgnoreCase(extension)) {
			fileType =RaboConstatnt.CSV;
		}
		else if (file.isEmpty() || !RaboConstatnt.CSV.equalsIgnoreCase(extension) || !RaboConstatnt.XML.equalsIgnoreCase(extension)) {
			logger.error("Invalid Input File.Please Upload csv file with extension (.csv) or xml file with extension (.xml)"); 
			throw new RaboException("Invalid Input File.Please Upload csv file with extension (.csv) or xml file with extension (.xml)");
		}
		
		RaboRecordResponse raboResponse = reboUtil.loadAndValidateData(file.getInputStream(), fileType);
		response.addHeader(RaboConstatnt.ACCESS_CONTROL, RaboConstatnt.STAR);
		return new ResponseEntity<RaboRecordResponse>(raboResponse, HttpStatus.OK);
	}

	
	
}