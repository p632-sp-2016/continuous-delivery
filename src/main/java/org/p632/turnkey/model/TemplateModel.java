package org.p632.turnkey.model;

import java.util.ArrayList;

public class TemplateModel {


	private String projectGroup;
	private String artifact;
	private String packageName;
	private ArrayList<String> dependencyList =	new ArrayList<String>();
	private String packagingType;

	public TemplateModel(TemplateModel json)
	{
		this.projectGroup = json.projectGroup;
		this.artifact 	  = json.artifact;
		this.packageName = json.packageName;
		this.dependencyList   = json.dependencyList;
		this.packagingType  = json.packagingType;
	}
	
	
	TemplateModel()
	{
		
	}


	public String getProjectGroup() {
		return projectGroup;
	}


	public void setProjectGroup(String projectGroup) {
		this.projectGroup = projectGroup;
	}


	public String getArtifact() {
		return artifact;
	}


	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}


	public String getPackageName() {
		return packageName;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}


	public ArrayList<String> getDependencyList() {
		return dependencyList;
	}


	public void setDependencyList(ArrayList<String> dependencyList) {
		this.dependencyList = dependencyList;
	}


	public String getPackagingType() {
		return packagingType;
	}


	public void setPackagingType(String packagingType) {
		this.packagingType = packagingType;
	}
	

}
