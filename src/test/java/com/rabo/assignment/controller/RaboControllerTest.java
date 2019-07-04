package com.rabo.assignment.controller;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rabo.assignment.service.RaboService;
import com.rabo.assignment.utils.RaboUtil;

@RunWith(MockitoJUnitRunner.class)
public class RaboControllerTest {

	InputStream is;
	MockMvc mockMvc;

	@Mock
	RaboService raboService;

	@InjectMocks 
	RaboController controller = new RaboController();

	@Mock
	RaboUtil raboUtil;
	
	@Mock
    ResourceLoader resourceLoader;

	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Resource resource = new ClassPathResource("records.csv");
		is = resource.getInputStream();
	}

	@Test
	public void testUploadFile() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.csv", "multipart/form-data", is);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/repo/assignment/v1/auth/rebo-file-upload").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
		Assert.assertEquals(200, result.getResponse().getStatus());
		Assert.assertNotNull(result.getResponse().getContentAsString());
	}


}
