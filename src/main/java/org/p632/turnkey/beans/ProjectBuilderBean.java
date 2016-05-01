package org.p632.turnkey.beans;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.p632.turnkey.helpers.Utilities;
import org.p632.turnkey.model.TemplateModel;
import org.p632.turnkey.pom.Dependency;
import org.p632.turnkey.pom.DependencyList;
import org.p632.turnkey.pom.Pom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * The bean to create a maven project template.
 */
@Component
public class ProjectBuilderBean {

	final Logger logger = LoggerFactory.getLogger(BambooConfigurationBean.class);

	@Autowired
	private ResourceLoader resourceLoader;

	@Value("${test.serverPath}")
	private String serverPath;

	@Value("${test.templatePath}")
	private String templatePath;

	public List<String> getDependencyList() throws Exception {
		List<String> listDeps = new ArrayList<String>();

		try {
			serverPath = serverPath.replace(".", File.separator);
			Resource resource = resourceLoader.getResource("file:" + serverPath + "/dependency.xml");
			File xmlFile = resource.getFile();

			ObjectMapper xmlMapper = new XmlMapper();
			String xml = Utilities.getAllLines(xmlFile.getAbsolutePath());
			xml = xml.replace("\r\n", "");
			xml = xml.replace("\t", "");
			DependencyList knownDependencies = xmlMapper.readValue(xml, DependencyList.class);

			for (Dependency knownDependency : knownDependencies.dependency) {
				listDeps.add(knownDependency.artifactId);
			}

		} catch (Exception ex) {
			logger.error("Error getting dependency list", ex);
			throw ex;
		}

		return listDeps;
	}

	public void generatePom(TemplateModel templateModel) throws Exception {
		ObjectMapper xmlMapper = new XmlMapper();
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String xml;

		try {
			Pom pom = new Pom();
			pom.artifactId = templateModel.getArtifact();
			pom.groupId = templateModel.getProjectGroup();
			pom.packaging = templateModel.getPackagingType();
			pom.parent.groupId = templateModel.getParentProjectGroup();
			pom.parent.artifactId = templateModel.getParentArtifact();
			
			prepareDependencyXml(templateModel.getDependencyList(), pom);

			xml = xmlMapper.writeValueAsString(pom);

			serverPath = serverPath.replace(".", File.separator);
			templatePath = templatePath.replace(".", File.separator);
			Resource resource = resourceLoader.getResource("file:" + templatePath + "/pom.xml");
			File xmlFile = resource.getFile();
			String filePath = xmlFile.getAbsolutePath();

			PrintWriter out = new PrintWriter(filePath);
			out.println(xml);
			out.close();

		} catch (Exception ex) {
			logger.error("Error generating pom.xml", ex);
			throw ex;
		}
	}

	public void prepareDependencyXml(ArrayList<String> dependencyList, Pom pom) throws Exception {
		try {
			serverPath = serverPath.replace(".", File.separator);
			Resource resource = resourceLoader.getResource("file:" + serverPath + "/dependency.xml");
			File xmlFile = resource.getFile();
			ObjectMapper xmlMapper = new XmlMapper();

			String xml = Utilities.getAllLines(xmlFile.getAbsolutePath());
			xml = xml.replace("\r\n", "");
			xml = xml.replace("\t", "");
			DependencyList knownDependencies = xmlMapper.readValue(xml, DependencyList.class);

			for (String selectedDependency : dependencyList) {
				for (Dependency knownDependency : knownDependencies.dependency) {
					if (selectedDependency.equals(knownDependency.artifactId)) {
						pom.AddDependency(knownDependency.groupId, knownDependency.artifactId, knownDependency.version);
					}
				}
			}

		} catch (Exception ex) {
			logger.error("Error parsing dependency file", ex);
			throw ex;
		}
	}
}
