/**
 * Vivek Patani {FlipSwitch}
 * ProjectBuilderBeanTest.java
 * {Algorithms 0.: Living in Beta}
 */
package org.p632.turnkey.beans;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectBuilderBeanTest {
	
	//Decleration of the Class Instance
	@Mock
	ProjectBuilderBean projectBuilderBean;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//Initialise the mocking of the class
		projectBuilderBean = Mockito.mock(ProjectBuilderBean.class);
	}
	
	@Test
	public void getDependencyListTest() throws Exception {
		
		ArrayList<String> result = new ArrayList<String>();
		result.add("a");
		result.add("b");
		List<String> output = projectBuilderBean.getDependencyList();
		
		System.out.println(output);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		projectBuilderBean = null;
	}
}
