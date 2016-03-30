/**
 * Vivek Patani {FlipSwitch}
 * ProjectBuilderControllerTest.java
 * {Algorithms 0.: Living in Beta}
 */
package org.p632.turnkey.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.p632.turnkey.model.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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
		result.add("junit");
		result.add("spring-core");
		result.add("spring-context");
		result.add("spring-webmvc");
		result.add("jackson-jaxrs-base");
		
		
		mockMvc.perform(get("/projectbuilder/dependencyList")).andExpect(status().isOk());
		mockMvc.perform(get("/projectbuilder/dependencyList").accept(MediaType.APPLICATION_JSON_UTF8))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$", Matchers.hasSize(5)))
							.andExpect(jsonPath("$", Matchers.equalTo((result))));
	}
	
	
	@Test
	public void createTemplateMockTest() throws Exception {
		
		TemplateModel templateModel = new TemplateModel();
		templateModel.setArtifact("Sujeet");
		
		mockMvc.perform(post("/projectbuilder/buildTemplate")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
				//.body(templateModel));
//				.andReturn());
	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mockMvc = null;
	}

}
