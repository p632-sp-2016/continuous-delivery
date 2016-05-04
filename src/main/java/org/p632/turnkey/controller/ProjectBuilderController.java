package org.p632.turnkey.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.p632.turnkey.beans.BambooConfigurationBean;
import org.p632.turnkey.beans.GitConfigurationBean;
import org.p632.turnkey.beans.ProjectBuilderBean;
import org.p632.turnkey.helpers.Constants;
import org.p632.turnkey.model.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${test.orginizationName}")
	private String orginizationName;

	/**
	 * This method loads the dependency from the file on the server
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dependencyList")
	public ResponseEntity<?> loadDependencyList(ModelMap model) {
		List<String> dependencyList;
		try {
			dependencyList = builderBean.getDependencyList();
		} catch (Exception e) {
			TemplateModel templateModel = new TemplateModel();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			templateModel.setErrMsg(exceptionAsString);
			return new ResponseEntity<TemplateModel>(templateModel, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<String>>(dependencyList, HttpStatus.OK);
	}

	/**
	 * This method is responsible for creating a template project,configuring
	 * the project on git and building the project on bamboo.
	 * 
	 * @param templateModel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/buildTemplate")
	public ResponseEntity<?> createTemplate(@RequestBody TemplateModel templateModel) {
		templateModel.setReturnMsg(null);
		try {
			builderBean.generatePom(templateModel);
			int returnStatus = gitConfigurationBean.createRemoteRepos(templateModel.getArtifact());
			if (returnStatus == 201) {
				if (gitConfigurationBean.pushLocalRepos(templateModel) == "OK") {
					if (gitConfigurationBean.addTeamToRepo(templateModel) == 204) {
						templateModel.setOrganizationName(orginizationName);
						templateModel.setReturnMsg(Constants.SUCCESS);
					} else {
						templateModel.setReturnMsg(Constants.FAILURE);
						templateModel.setErrMsg("Error adding teams to remote repository");
						return new ResponseEntity<TemplateModel>(templateModel, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					templateModel.setReturnMsg(Constants.FAILURE);
					templateModel.setErrMsg("Error pushing to remote repository");
					return new ResponseEntity<TemplateModel>(templateModel, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				templateModel.setReturnMsg(Constants.FAILURE);
				templateModel.setErrMsg("Error creating remote repository");
				return new ResponseEntity<TemplateModel>(templateModel, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			bambooConfigurationBean.processBuild(templateModel.getArtifact());
			return new ResponseEntity<TemplateModel>(templateModel, HttpStatus.OK);

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			templateModel.setErrMsg(exceptionAsString);
			return new ResponseEntity<TemplateModel>(templateModel, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
