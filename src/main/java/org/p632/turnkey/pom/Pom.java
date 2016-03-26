package org.p632.turnkey.pom;

import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "project")
public class Pom{
	@JacksonXmlProperty(localName = "modelVersion")
    public String modelVersion = "1.0.0";
	
	@JacksonXmlProperty(localName = "groupId")
    public String groupId = "com.test";
	
	@JacksonXmlProperty(localName = "artifactId")
    public String artifactId = "test_app";
	
	@JacksonXmlProperty(localName = "version")
    public String version = "0.0.1-SNAPSHOT";
	
	@JacksonXmlProperty(localName = "packaging")
    public String packaging = "jar";

	@JacksonXmlElementWrapper(localName = "dependencies")
    @JacksonXmlProperty(localName = "dependency")    
	public ArrayList<Dependency> deplist = new ArrayList<Dependency>();
	
    public Pom()
    {
    	//dependencies.add("hello");
    	
    }
    
    public void AddDependency(String groupId, String artifact, String version)
    {
    	Dependency dependency = new Dependency();
    	dependency.groupId = groupId;
    	dependency.artifactId = artifact;
    	dependency.version = version;
    	
    	deplist.add(dependency);
    }
}
