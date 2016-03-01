package org.p632.turnkey.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

	public List<String> getDependencyList() {
		List<String> listDeps = new ArrayList<String>();

		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File xmlFile = new File(classLoader.getResource("dependency.xml").getFile());
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listDeps;
	}
}
