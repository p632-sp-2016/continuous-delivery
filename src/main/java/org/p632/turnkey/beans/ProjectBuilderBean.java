package org.p632.turnkey.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The bean to create a maven project template.
 */
@Component
public class ProjectBuilderBean {

	@Autowired
	private ResourceLoader resourceLoader;

	@Value("${test.serverPath}")
	private String serverPath;

	public List<String> getDependencyList() throws Exception {
		List<String> listDeps = new ArrayList<String>();

		serverPath = serverPath.replace(".", File.separator);
		Resource resource = resourceLoader.getResource("file:" + serverPath + "/dependency.xml");
		File xmlFile = resource.getFile();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		NodeList nList = doc.getElementsByTagName("dependency");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				String dependency = eElement.getElementsByTagName("artifactId").item(0).getTextContent();
				listDeps.add(dependency);
			}
		}

		return listDeps;
	}
}
