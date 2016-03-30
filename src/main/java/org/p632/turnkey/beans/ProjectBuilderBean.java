package org.p632.turnkey.beans;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
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
import org.p632.turnkey.helpers.Utilities;
import org.p632.turnkey.model.TemplateModel;
import org.p632.turnkey.pom.Dependency;
import org.p632.turnkey.pom.DependencyList;
import org.p632.turnkey.pom.Pom;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

	@Value("${test.templatePath}")
	private String templatePath;
	
	public List<String> getDependencyList() {
		List<String> listDeps = new ArrayList<String>();

		try {
			serverPath = serverPath.replace(".", File.separator);
			Resource resource = resourceLoader.getResource("file:"+ serverPath +"/dependency.xml");
			//Resource resource = resourceLoader.getResource("file:"+ serverPath +"/projectTemplate/pom.xml");
			//serverPath = serverPath.replace(".", File.separator);
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
	
	public void generatePom(TemplateModel templateModel)
	{    	
    	ObjectMapper xmlMapper = new XmlMapper();
    	String xml;    	
		
		try {
			Pom pom = new Pom();
			pom.artifactId = templateModel.getArtifact();
			pom.groupId = templateModel.getProjectGroup();
			pom.packaging = templateModel.getPackagingType();
			pom.parent.groupId = templateModel.getParentProjectGroup();
			pom.parent.artifactId = templateModel.getParentArtifact();
			PrepareDependencyXml(templateModel.getDependencyList(),pom);
			
			xml = xmlMapper.writeValueAsString(pom);
			
			serverPath = serverPath.replace(".", File.separator);
			templatePath = templatePath.replace(".", File.separator);
			Resource resource = resourceLoader.getResource("file:" + templatePath +"/pom.xml");			
			File xmlFile = resource.getFile();
			String filePath = xmlFile.getAbsolutePath();
			
			PrintWriter out = new PrintWriter( filePath);
			out.println( xml );
			out.close();			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void PrepareDependencyXml(ArrayList<String> dependencyList, Pom pom)
	{		
		try {
			serverPath = serverPath.replace(".", File.separator);
			Resource resource = resourceLoader.getResource("file:"+ serverPath +"/dependency.xml");
			File xmlFile = resource.getFile();			
			ObjectMapper xmlMapper = new XmlMapper();
			
			String xml = Utilities.GetAllLines(xmlFile);
			xml = xml.replace("\r\n", "");
			xml = xml.replace("\t", "");
			DependencyList knownDependencies = xmlMapper.readValue(xml, DependencyList.class);		
		
			for (String selectedDependency : dependencyList) {
				if(selectedDependency == null)
				{
					break;
				}
				
				for (Dependency knownDependency : knownDependencies.dependency )
				{					
					if(selectedDependency.equals(knownDependency.artifactId))
					{
						pom.AddDependency(knownDependency.groupId, knownDependency.artifactId, knownDependency.version);
					}
				}				
			}
		
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

