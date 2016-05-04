package org.p632.turnkey.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.p632.turnkey.helpers.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Component responsible for building the teamplate project on Bamboo.
 * 
 * @author Rohit Surve
 *
 */
@Component
public class BambooConfigurationBean {

	@Value("${test.serverPath}")
	private String serverPath;

	final Logger logger = LoggerFactory.getLogger(BambooConfigurationBean.class);

	/**
	 * This method executes the bamboo shell script which in turn creates and builds plans
	 * for the template project.Template project name and git repository url is provided as an input 
	 * to the shell script.
	 * 
	 * @param templateName
	 * @throws Exception
	 */
	public void processBuild(String templateName) throws Exception {
		serverPath = serverPath.replace(".", File.separator);
		String[] buildArgs = { "/bin/bash", serverPath + "/"+Constants.BAMBOO_SCRIPT, templateName,
				Constants.GIT_REPOSITORY };
		Process process;
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(buildArgs);
			processBuilder.directory(new File(serverPath));
			process = processBuilder.start();

			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				logger.info(line);
			}

			BufferedReader brErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String lineError;
			while ((lineError = brErr.readLine()) != null) {
				logger.error(lineError);
				throw new Exception(lineError);
			}

			logger.debug("Bamboo build completed succesfully");
		} catch (Exception ex) {
			logger.error("Error during Bamboo build configuration", ex);
			throw ex;			
		}
	}

}
