/**
 * Vivek Patani {FlipSwitch}
s
 * GitConfigurationBeanTest.java
 * {Algorithms 0.: Living in Beta}
 */
package org.p632.turnkey.beans;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * 
 */

public class GitConfigurationBeanTest {

	GitConfigurationBean gitConfigurationBean;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gitConfigurationBean = Mockito.mock(GitConfigurationBean.class);
	}

	@Test
	public void createRemoteReposTestWithName() throws Exception{
		String remoteReposName = "Sujeet";
		
		//To Mock the method and check the return status
		when(gitConfigurationBean.createRemoteRepos(remoteReposName)).thenReturn(201);
		
		//Verify what executed was correct
		assertEquals(gitConfigurationBean.createRemoteRepos(remoteReposName), 201);
		
		//Verify whether if the method even ran or not at all
		verify(gitConfigurationBean, atLeastOnce()).createRemoteRepos(remoteReposName);
		
		//To verify the calling not more than once for one createRemoteRepos
		//Put 2 to verify, the test should fail on any input apart from 1
		verify(gitConfigurationBean, times(1)).createRemoteRepos(remoteReposName);
	}
	
	@Test
	public void createRemoteReposTestWithNoName() throws Exception{
		String remoteReposName = "";
		
		//To Mock the method and check the return status
		when(gitConfigurationBean.createRemoteRepos(remoteReposName)).thenReturn(201);
		
		//Verify what executed was correct
		assertEquals(gitConfigurationBean.createRemoteRepos(remoteReposName), 201);
		
		//Verify whether if the method even ran or not at all
		verify(gitConfigurationBean, atLeastOnce()).createRemoteRepos(remoteReposName);
		
		//To verify the calling not more than once for one createRemoteRepos
		//Put 2 to verify, the test should fail on any input apart from 1
		verify(gitConfigurationBean, times(1)).createRemoteRepos(remoteReposName);
	}
	
	@Test
	public void pushRemoteReposTestWithName() throws Exception{
		String remoteReposName = "Sujeet";
		
		//To Mock the method and check the return status
		when(gitConfigurationBean.createRemoteRepos(remoteReposName)).thenReturn(201);
		
		//Verify what executed was correct
		assertEquals(gitConfigurationBean.createRemoteRepos(remoteReposName), 201);
		
		//Verify whether if the method even ran or not at all
		verify(gitConfigurationBean, atLeastOnce()).createRemoteRepos(remoteReposName);
		
		//To verify the calling not more than once for one createRemoteRepos
		//Put 2 to verify, the test should fail on any input apart from 1
		verify(gitConfigurationBean, times(1)).createRemoteRepos(remoteReposName);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		gitConfigurationBean = null;
	}
}
