/**
 * Vivek Patani {FlipSwitch}
 * TestControllerTest.java
 * {Algorithms 0.: Living in Beta}
 */
package org.p632.turnkey.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 */
@ContextConfiguration(locations = { "classpath:integrator-rest.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testReadConfig() throws Exception {
		mockMvc.perform(get("/test/config").accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}

	@Test
	public void testLoadDependencyList() throws Exception {
		List<String> depList = new ArrayList<String>();
		depList.add("spring-core-3.0.1");
		depList.add("spring-webmvc-4.2.1");
		depList.add("spring-test-4.0.1");
		depList.add("junit-4.1");

		mockMvc.perform(get("/test/dependencyList")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(depList.size())));
	}
	
	@Before
	public void tearDown() {
		mockMvc = null;
		}
}