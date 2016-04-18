package org.p632.turnkey.beans;

import java.io.File;

import org.p632.turnkey.helpers.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BambooConfigurationBean {

	@Value("${test.serverPath}")
	private String serverPath;

	final Logger logger = LoggerFactory.getLogger(BambooConfigurationBean.class);

	public void processBuild(String templateName) {
		serverPath = serverPath.replace(".", File.separator);
		String[] buildArgs = { "/bin/bash",templateName, serverPath + Constants.BAMBOO_SCRIPT,
				Constants.GIT_REPOSITORY};

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(buildArgs);
			processBuilder.directory(new File(serverPath));
			processBuilder.start();
			logger.debug("Bamboo build completed succesfully");
		} catch (Exception ex) {
			logger.error("Error during Bamboo build configuration", ex);
		}
	}

}
