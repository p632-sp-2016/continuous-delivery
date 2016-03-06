package org.p632.turnkey.controller;

import java.util.ArrayList;
import java.util.List;

import org.p632.turnkey.beans.TestBean;
import org.p632.turnkey.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * Test Class to validate spring rest flow.
 *
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

	@Autowired
	private TestBean testBean;

	@RequestMapping(value = "/config")
	public TestModel readConfig(ModelMap model) {
		String userCred = testBean.testMethod();
		model.addAttribute("userCred", userCred);
		TestModel testModel = new TestModel();
		String cred[] = userCred.split(" ");
		testModel.setTestString1(cred[0]);
		testModel.setTestString2(cred[1]);
		return testModel;
	}
	
	@RequestMapping(value = "/dependencyList")
	public List<String> loadDependencyList(ModelMap model) {
		List<String> depList = new ArrayList<String>();
		depList.add("spring-core-3.0.1");
		depList.add("spring-webmvc-4.2.1");
		depList.add("spring-test-4.0.1");
		depList.add("junit-4.1");
		return depList;
	}
	
	@RequestMapping(value = "/createTemplate")
	public void createTemplate(@RequestBody String details) {
		//System.out.println(details);
	}

}
