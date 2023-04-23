package com.contus.searchapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ContusapiApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void paginationApiTest() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/searchApi/searchEmployee?page=0&size=10")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		JSONObject resJsonObject = new JSONObject();
		JSONParser parser = new JSONParser();
		resJsonObject = (JSONObject) parser.parse(contentAsString);
		System.out.println(contentAsString);
		assertEquals(false, resJsonObject.get("isError"));
	}

}
