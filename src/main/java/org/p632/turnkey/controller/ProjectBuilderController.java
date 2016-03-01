package org.p632.turnkey.controller;

import java.util.List;

import org.p632.turnkey.beans.GitConfigurationBean;
import org.p632.turnkey.beans.ProjectBuilderBean;
import org.p632.turnkey.model.TemplateModel;
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
	private ProjectBuilderBean builderBean;

	@Autowired
	private GitConfigurationBean gitConfigurationBean;

	@RequestMapping(value = "/dependencyList")
	public List<String> loadDependencyList(ModelMap model) {

		return builderBean.getDependencyList();
	}

	@RequestMapping(value = "/buildTemplate")
	public void createTemplate(@RequestBody TemplateModel details) {

		int returnStatus = gitConfigurationBean.createRemoteRepos(details.getArtifact());
		if (returnStatus == 201) {
			gitConfigurationBean.pushLocalRepos(details.getArtifact());
			// TODO return proper status message to UI
		} else {
			// // TODO return result back to UI
		}
	}
}
