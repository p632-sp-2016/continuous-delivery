/**
 * Vivek Patani {FlipSwitch}
 * ProjectBuilderControllerTest.java
 * {Algorithms 0.: Living in Beta}
 */
package org.p632.turnkey.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
public class ProjectBuilderControllerTest {
	
	@Mock
    private ProjectBuilderController projectBuilderControllerMock;
     
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MockitoAnnotations.initMocks(this);
		}

	@Test
	public void loadDependencyListMockTest() throws Exception {
		
		ArrayList<String> result = new ArrayList<String>();
		result.add("spring-core-3.0.1");
		result.add("spring-webmvc-4.2.1");
		result.add("spring-test-4.0.1");
		result.add("junit-4.1");
		
		mockMvc.perform(get("/test/dependencyList")).andExpect(status().isOk());
		mockMvc.perform(get("/test/dependencyList").accept(MediaType.APPLICATION_JSON_UTF8))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$", Matchers.hasSize(4)))
							.andExpect(jsonPath("$", Matchers.equalTo((result))));
	}
	
	@Test
	public void createTemplateMockTest() throws Exception {
		
		ArrayList<String> result = new ArrayList<String>();
		result.add("spring-core-3.0.1");
		result.add("spring-webmvc-4.2.1");
		result.add("spring-test-4.0.1");
		result.add("junit-4.1");
		
		mockMvc.perform(get("/test/dependencyList")).andExpect(status().isOk());
		mockMvc.perform(get("/test/dependencyList").accept(MediaType.APPLICATION_JSON_UTF8))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$", Matchers.hasSize(4)))
							.andExpect(jsonPath("$", Matchers.equalTo((result))));
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mockMvc = null;
	}

}
