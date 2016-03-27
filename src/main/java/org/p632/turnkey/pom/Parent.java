package org.p632.turnkey.pom;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "parent")
public class Parent {

	@JacksonXmlProperty(localName = "groupId")
    public String groupId = "com.parent.def_grp";

    @JacksonXmlProperty(localName = "artifactId")
    public String artifactId = "def_dep_parent";
    
    @JacksonXmlProperty(localName = "version")
    public String version = "1.0.0";
}
