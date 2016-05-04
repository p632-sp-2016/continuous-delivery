package org.p632.turnkey.model;

import java.io.Serializable;
import java.util.ArrayList;

public class TemplateModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectGroup;
	private String parentProjectGroup;
	private String parentArtifact;
	private String artifact;
	private String packageName;
	private ArrayList<String> dependencyList = new ArrayList<String>();
	private String packagingType;
	private String returnMsg;
	private String errMsg;
	private String organizationName;

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getParentProjectGroup() {
		return parentProjectGroup;
	}

	public void setParentProjectGroup(String parentProjectGroup) {
		this.parentProjectGroup = parentProjectGroup;
	}
	
	public String getParentArtifact() {
		return parentArtifact;
	}

	public void setParentArtifact(String parentArtifact) {
		this.parentArtifact = parentArtifact;
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

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	

}
