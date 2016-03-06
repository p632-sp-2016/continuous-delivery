package org.p632.turnkey.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * Test bean class
 *
 */
@Component
public class TestBean {

	@Value("${test.username}")
	private String userName;

	@Value("${test.password}")
	private String password;

	public String testMethod() {
		return userName + " " + password;
	}
	
	

}
