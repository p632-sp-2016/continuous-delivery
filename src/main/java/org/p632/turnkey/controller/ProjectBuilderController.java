package org.p632.turnkey.controller;

import java.util.ArrayList;
import java.util.List;

import org.p632.turnkey.beans.ProjectBuilderBean;
import org.p632.turnkey.beans.TestBean;
import org.p632.turnkey.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * This is the rest controller for github project builder module.
 *
 */
@RestController
@RequestMapping(value = "/projectbuilder")
public class ProjectBuilderController {

	@Autowired
	private ProjectBuilderBean _builderBean;
		
	@RequestMapping(value = "/dependencyList")
	public List<String> loadDependencyList(ModelMap model) {
		
		return _builderBean.GetDependencyList();
	}
}
