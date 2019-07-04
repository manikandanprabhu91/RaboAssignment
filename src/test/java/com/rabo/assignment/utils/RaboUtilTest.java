package com.rabo.assignment.utils;

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

@RunWith(MockitoJUnitRunner.class)
public class RaboUtilTest {

	@Mock
	RaboUtil raboUtil;
	
	InputStream inputStream;
	
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        inputStream = raboUtil.getClass().getClassLoader().getResourceAsStream("excel.xlsx");
    }
	
	@Test
	public void testLoadAndValidateData() {
		List<RaboResponse> raboResponses = new ArrayList<>();
		RaboResponse raboResponse = new RaboResponse();
		raboResponse.setDescription("Test");
		raboResponse.setEndBalance("74.23");
		raboResponse.setRefernce("123012");
		raboResponses.add(raboResponse);
		RaboRecordResponse response = new RaboRecordResponse();
		response.setMessage("Successfully loaded");
		response.setRaboResponse(raboResponses);
		Mockito.when(raboUtil.loadAndValidateData(inputStream, "csv")).thenReturn(response);
		RaboRecordResponse raboResponseResult = raboUtil.loadAndValidateData(inputStream, "csv");
		assertEquals(raboResponseResult.getMessage(), "Successfully loaded");
	}
	
}
