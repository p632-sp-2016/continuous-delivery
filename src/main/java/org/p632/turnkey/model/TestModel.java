package org.p632.turnkey.model;

import java.io.Serializable;

/**
 * 
 * Test model
 *
 */
public class TestModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String testString1;

	private String testString2;

	public String getTestString1() {
		return testString1;
	}

	public void setTestString1(String testString1) {
		this.testString1 = testString1;
	}

	public String getTestString2() {
		return testString2;
	}

	public void setTestString2(String testString2) {
		this.testString2 = testString2;
	}

}
