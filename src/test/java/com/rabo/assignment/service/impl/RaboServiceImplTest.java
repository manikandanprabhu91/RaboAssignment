package com.rabo.assignment.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.rabo.assignment.domain.RaboRecordResponse;
import com.rabo.assignment.domain.RaboResponse;
import com.rabo.assignment.service.RaboService;
import com.rabo.assignment.utils.RaboUtil;

@RunWith(MockitoJUnitRunner.class)
public class RaboServiceImplTest {
	
	@InjectMocks
	RaboService raboService = new RaboServiceImpl();
	
	InputStream inputStream;
	
	@Mock
	RaboUtil raboUtil;
	
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        inputStream = raboService.getClass().getClassLoader().getResourceAsStream("excel.xlsx");
    }
	
	@Test
	public void testDataProcessor() {
		List<RaboResponse> raboResponses = new ArrayList<>();
		RaboResponse raboResponse = new RaboResponse();
		raboResponse.setDescription("Test");
		raboResponse.setEndBalance("74.23");
		raboResponse.setRefernce("123012");
		raboResponses.add(raboResponse);
		RaboRecordResponse response = new RaboRecordResponse();
		response.setMessage("Successfully loaded");
		response.setRaboResponse(raboResponses);
		Mockito.when(raboService.dataProcessor(inputStream, "csv")).thenReturn(response);
		RaboRecordResponse raboResponseResult = raboService.dataProcessor(inputStream, "csv");
		assertEquals(raboResponseResult.getMessage(), "Successfully loaded");
	}

}
