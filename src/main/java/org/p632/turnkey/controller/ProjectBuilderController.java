package org.p632.turnkey.controller;

import java.util.List;

import org.p632.turnkey.beans.GitConfigurationBean;
import org.p632.turnkey.beans.ProjectBuilderBean;
import org.p632.turnkey.model.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> loadDependencyList(ModelMap model) {
		List<String> dependencyList = builderBean.getDependencyList();
		return new ResponseEntity<List<String>>(dependencyList, HttpStatus.OK);
	}

	@RequestMapping(value = "/buildTemplate")
	public ResponseEntity<?> createTemplate(@RequestBody TemplateModel templateModel) throws Exception {

		int returnStatus = gitConfigurationBean.createRemoteRepos(templateModel.getArtifact());
		if (returnStatus == 201) {
			gitConfigurationBean.pushLocalRepos(templateModel.getArtifact());
			templateModel.setReturnMsg("success");
		}else{
			templateModel.setReturnMsg("failure");
			return new ResponseEntity<TemplateModel>(templateModel,HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<TemplateModel>(templateModel,HttpStatus.OK);

	}
}
