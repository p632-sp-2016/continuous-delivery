package org.p632.turnkey.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

import org.p632.turnkey.pom.Pom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * The bean to create a maven project template.
 */
@Component
public class ProjectBuilderBean {
	
	@Autowired
    private ResourceLoader resourceLoader;
	
	@Value("${test.serverPath}")
	private String serverPath;

	public List<String> getDependencyList() {
		List<String> listDeps = new ArrayList<String>();

		try {
			serverPath = serverPath.replace(".", File.separator);
			Resource resource = resourceLoader.getResource("file:"+ serverPath +"/dependency.xml");
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listDeps;
	}
	
	public void generatePom()
	{
    	ObjectMapper mapper = new XmlMapper();
    	ObjectMapper xmlMapper = new XmlMapper();
    	String xml;
		try {
			Pom pom = new Pom();
			pom.AddDependency("groupId1", "artifact1", "version1");
			pom.AddDependency("groupId2", "artifact2", "version2");
			xml = xmlMapper.writeValueAsString(pom);
			PrintWriter out = new PrintWriter("C:\\xml\\Pom.xml");
			out.println( xml );
			out.close();
			System.out.println(xml );
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

