/**
 * Vivek Patani {FlipSwitch}
 * BambooConfigurationBeanTest.java
 * {Algorithms 0.: Living in Beta}
 */
package org.p632.turnkey.beans;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooConfigurationBeanTest {

	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test(expected = Exception.class)
	public void processBuildTest() throws Exception {
		BambooConfigurationBean spyBean = Mockito.spy(new BambooConfigurationBean());
		String templateName = "Rohit";
		ReflectionTestUtils.setField(spyBean, "serverPath", ".home.softeng.students.spring2016.FrancisCD");

		spyBean.processBuild(templateName);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
}
