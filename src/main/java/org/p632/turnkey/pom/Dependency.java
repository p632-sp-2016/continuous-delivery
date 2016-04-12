package org.p632.turnkey.pom;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "dependency")
public class Dependency {
	@JacksonXmlProperty(localName = "groupId")
    public String groupId = "com.def_grp";

    @JacksonXmlProperty(localName = "artifactId")
    public String artifactId = "def_dep";
    
    @JacksonXmlProperty(localName = "version")
    public String version = "LATEST";
}