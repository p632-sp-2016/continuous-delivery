package org.p632.turnkey.controller;

import java.util.List;

import org.p632.turnkey.beans.BambooConfigurationBean;
import org.p632.turnkey.beans.GitConfigurationBean;
import org.p632.turnkey.beans.ProjectBuilderBean;
import org.p632.turnkey.helpers.Constants;
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
	
	@Autowired
	private BambooConfigurationBean bambooConfigurationBean;

	/**
	 * This method loads the dependency from the file on the server
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dependencyList")
	public ResponseEntity<?> loadDependencyList(ModelMap model) {
		List<String> dependencyList = builderBean.getDependencyList();
		return new ResponseEntity<List<String>>(dependencyList, HttpStatus.OK);
	}

	/**
	 * This method is responsible for creating a template project,configuring the project on git and
	 * building the project on bamboo.
	 * 
	 * @param templateModel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/buildTemplate")
	public ResponseEntity<?> createTemplate(@RequestBody TemplateModel templateModel) throws Exception {

		int returnStatus = gitConfigurationBean.createRemoteRepos(templateModel.getArtifact());
		if (returnStatus == 201) {
			builderBean.generatePom(templateModel);
			if(gitConfigurationBean.pushLocalRepos(templateModel)=="OK"){
				if(gitConfigurationBean.addTeamToRepo(templateModel)==204){
					templateModel.setReturnMsg("success");
				}
				else{
					templateModel.setReturnMsg("failure");
					}
			}else{
				templateModel.setReturnMsg("failure");
			}
		}else{
			templateModel.setReturnMsg("failure");
			return new ResponseEntity<TemplateModel>(templateModel,HttpStatus.BAD_REQUEST);
		}
				
		return new ResponseEntity<TemplateModel>(templateModel,HttpStatus.OK);

	}
}
