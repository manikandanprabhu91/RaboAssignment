package com.rabo.assignment.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.rabo.assignment.constant.RaboConstatnt;
import com.rabo.assignment.domain.RaboRecordResponse;
import com.rabo.assignment.domain.RaboResponse;
import com.rabo.assignment.domain.Record;
import com.rabo.assignment.domain.Records;
import com.rabo.assignment.exptions.RaboException;

@Component
public class RaboUtil {

	private static final Logger logger = LogManager.getLogger(RaboUtil.class.getName());

	public RaboRecordResponse loadAndValidateData(InputStream inputStream, String fileType) throws RaboException
	{
		List<Record> recordList = new ArrayList<>();
		RaboRecordResponse reboResponse = null;
	
		logger.info("::::::::::loadAndValidateData csvparser start ::::::::::::");
		try {
			if(fileType.equals(RaboConstatnt.CSV)) {
				CSVParser csvParser = CSVFormat.EXCEL.withHeader().
						parse(new InputStreamReader(inputStream));
				logger.info("::::::::::loadAndValidateData csvparser end ::::::::::::");
				for(CSVRecord csvRecord : csvParser) {
					Record record = new Record();
					record.setRefernce(Integer.valueOf(csvRecord.get(RaboConstatnt.REFERENCE)));
					record.setAccountNumber(csvRecord.get(RaboConstatnt.ACCOUNT_NUMBER));
					record.setDescription(csvRecord.get(RaboConstatnt.DESCRIPTION));
					record.setStartBalance(csvRecord.get(RaboConstatnt.START_BALANCE));
					record.setMutation(csvRecord.get(RaboConstatnt.MUTATION));
					record.setEndBalance(csvRecord.get(RaboConstatnt.END_BALANCE));
					recordList.add(record);
				}
				reboResponse = ValidateRecord(recordList);
			} else if(fileType.equals(RaboConstatnt.XML)) {
				JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
	            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	            Records records = (Records) jaxbUnmarshaller.unmarshal(inputStream);
	            reboResponse = ValidateRecord(records.getRecord());
				reboResponse.setMessage("Successfully proccessed");
			}
		}catch (Exception e) {
			logger.error(":::::::::::::::::loadAndValidateData failure issue:::::::: " + e.getMessage());
			throw new RaboException("file loadAndValidateData failure issue::::::::" + e.getMessage());
		}

		return reboResponse;
	}

	protected RaboRecordResponse ValidateRecord(List<Record> recordList) throws RaboException{
		List<RaboResponse> reboResponse = new ArrayList<>();
		Map<String, String> recordMap = new HashMap<>();
		Map<String, String> balanceFailureMap = new HashMap<>();
		try {
			for (Record record : recordList) {
				RaboResponse repoResponse = null;
				BigDecimal startbal = BigDecimal.valueOf(Double.valueOf(record.getStartBalance())); 
				BigDecimal midbal = BigDecimal.valueOf(Double.valueOf(record.getMutation()));
				BigDecimal total = startbal.add(midbal);
				BigDecimal endbalance =  BigDecimal.valueOf(Double.valueOf(record.getEndBalance()));
				if((endbalance == total)) {
					repoResponse = setRepoRecordResponse(record);
					balanceFailureMap.put(String.valueOf(record.getRefernce()), String.valueOf(record.getRefernce()));
				}
				if(recordMap.size() == 0)
					recordMap.put(String.valueOf(record.getRefernce()), String.valueOf(record.getRefernce()));
				else if(recordMap.size() >= 0 && !recordMap.containsKey(String.valueOf(record.getRefernce()))) {
					recordMap.put(String.valueOf(record.getRefernce()), String.valueOf(record.getRefernce()));
				}else if(recordMap.size() >= 0 && recordMap.containsKey(String.valueOf(record.getRefernce()))) {

					if(balanceFailureMap.size() >=0 && !balanceFailureMap.containsKey(String.valueOf(record.getRefernce()))) {
						repoResponse = setRepoRecordResponse(record);
					} else {
						repoResponse = setRepoRecordResponse(record);
					}
					reboResponse.add(repoResponse);
				}
			}
		} catch (Exception e) {
			logger.error(":::::::::::::::::ValidateRecord failure issue:::::::: " + e.getMessage());
			throw new RaboException(":::::::::::::::::ValidateRecord failure issue:::::::: " + e.getMessage());
		}
		RaboRecordResponse recordResponse = new RaboRecordResponse();
		recordResponse.setRaboResponse(reboResponse);
		return recordResponse;
	}

	private RaboResponse setRepoRecordResponse(Record record) {
		RaboResponse reboResponse = new RaboResponse();
		reboResponse.setRefernce(String.valueOf(record.getRefernce()));
		reboResponse.setEndBalance(record.getEndBalance());
		reboResponse.setDescription(record.getDescription());
		return reboResponse;
	}

}
