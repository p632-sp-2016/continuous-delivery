package org.p632.turnkey.pom;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "dependencies")
public class DependencyList {		
	@JacksonXmlProperty(localName = "dependency")
    @JacksonXmlElementWrapper(useWrapping = false)
	public List<Dependency> dependency = new ArrayList<Dependency>();
}
