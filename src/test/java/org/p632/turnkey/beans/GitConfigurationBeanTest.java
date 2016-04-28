/**
 * Vivek Patani {FlipSwitch}
s
 * GitConfigurationBeanTest.java
 * {Algorithms 0.: Living in Beta}
 */
package org.p632.turnkey.beans;

import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.URIish;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.p632.turnkey.model.TemplateModel;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.expression.spel.ast.BooleanLiteral;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FileUtils.class, Git.class })
public class GitConfigurationBeanTest {

	@Mock
	DefaultHttpClient defaultHttpClient;

	@Mock
	HttpClient httpClient;

	@Mock
	HttpResponse res;

	@Mock
	StatusLine l;

	@Mock
	HttpEntity httpEntity;

	@Mock
	Git gitPush;

	@Mock
	AddCommand addCommand;

	@Mock
	InitCommand initCmd;

	@Mock
	FileUtils fileUtils;

	@Mock
	CommitCommand commitCmd;

	@Mock
	PushCommand pushCommand;

	@Mock
	Iterable<PushResult> itr;

	@Mock
	Iterator<PushResult> iter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// MockitoAnnotations.initMocks(GitConfigurationBeanTest.class);
	}

	@Test
	public void createRemoteReposTestWithName() throws Exception {
		GitConfigurationBean spyBean = Mockito.spy(new GitConfigurationBean());
		String remoteReposName = "dummyresponse";
		res.setStatusCode(201);

		ReflectionTestUtils.setField(spyBean, "gitApi", "vivek");
		when(spyBean.createHttpClient()).thenReturn(httpClient);
		when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(res);

		when(res.getStatusLine()).thenReturn(l);
		when(l.getStatusCode()).thenReturn(200);

		Assert.assertEquals(200, spyBean.createRemoteRepos(remoteReposName));
	}

	@Test
	public void createRemoteReposTestWithoutName() throws Exception {
		GitConfigurationBean spyBean = Mockito.spy(new GitConfigurationBean());
		String remoteReposName = null;
		res.setStatusCode(201);

		ReflectionTestUtils.setField(spyBean, "gitApi", "vivek");
		when(spyBean.createHttpClient()).thenReturn(httpClient);
		when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(res);

		when(res.getStatusLine()).thenReturn(l);
		when(l.getStatusCode()).thenReturn(200);

		Assert.assertEquals(200, spyBean.createRemoteRepos(remoteReposName));
	}

	@Test
	public void pushLocalReposTestWithName() throws Exception {
		GitConfigurationBean spyBean = Mockito.spy(new GitConfigurationBean());

		PowerMockito.mockStatic(FileUtils.class);
		PowerMockito.spy(Git.class);
		PowerMockito.when(Git.init()).thenReturn(initCmd);
		PowerMockito.when(initCmd.setDirectory(Mockito.any(File.class))).thenReturn(initCmd);
		PowerMockito.when(initCmd.call()).thenReturn(gitPush);

		Mockito.when(gitPush.add()).thenReturn(addCommand);
		Mockito.when(addCommand.addFilepattern(Mockito.anyString())).thenReturn(addCommand);
		;

		Mockito.when(gitPush.commit()).thenReturn(commitCmd);
		Mockito.when(commitCmd.setMessage(Mockito.anyString())).thenReturn(commitCmd);

		Mockito.when(gitPush.push()).thenReturn(pushCommand);
		Mockito.when(pushCommand.setCredentialsProvider(Mockito.any(CredentialsProvider.class)))
				.thenReturn(pushCommand);
		Mockito.when(pushCommand.setForce(Mockito.any(Boolean.class))).thenReturn(pushCommand);
		Mockito.when(pushCommand.setRemote(Mockito.any(String.class))).thenReturn(pushCommand);
		Mockito.when(pushCommand.setPushAll()).thenReturn(pushCommand);

		Mockito.when(pushCommand.call()).thenReturn(itr);
		Mockito.when(itr.iterator()).thenReturn(iter);

		ReflectionTestUtils.setField(spyBean, "serverPath", "vivek");
		ReflectionTestUtils.setField(spyBean, "remoteUrl", "vivek");
		ReflectionTestUtils.setField(spyBean, "templatePath", "vivek");

		TemplateModel model = new TemplateModel();
		model.setArtifact("dummy");
		model.setProjectGroup("Dummy_grp");

		Assert.assertTrue(null == spyBean.pushLocalRepos(model));

	}
	
	@Test
	public void pushLocalReposTestWithOutName() throws Exception {
		GitConfigurationBean spyBean = Mockito.spy(new GitConfigurationBean());

		PowerMockito.mockStatic(FileUtils.class);
		PowerMockito.spy(Git.class);
		PowerMockito.when(Git.init()).thenReturn(initCmd);
		PowerMockito.when(initCmd.setDirectory(Mockito.any(File.class))).thenReturn(initCmd);
		PowerMockito.when(initCmd.call()).thenReturn(gitPush);

		Mockito.when(gitPush.add()).thenReturn(addCommand);
		Mockito.when(addCommand.addFilepattern(Mockito.anyString())).thenReturn(addCommand);
		;

		Mockito.when(gitPush.commit()).thenReturn(commitCmd);
		Mockito.when(commitCmd.setMessage(Mockito.anyString())).thenReturn(commitCmd);

		Mockito.when(gitPush.push()).thenReturn(pushCommand);
		Mockito.when(pushCommand.setCredentialsProvider(Mockito.any(CredentialsProvider.class)))
				.thenReturn(pushCommand);
		Mockito.when(pushCommand.setForce(Mockito.any(Boolean.class))).thenReturn(pushCommand);
		Mockito.when(pushCommand.setRemote(Mockito.any(String.class))).thenReturn(pushCommand);
		Mockito.when(pushCommand.setPushAll()).thenReturn(pushCommand);

		Mockito.when(pushCommand.call()).thenReturn(itr);
		Mockito.when(itr.iterator()).thenReturn(iter);

		ReflectionTestUtils.setField(spyBean, "serverPath", "vivek");
		ReflectionTestUtils.setField(spyBean, "remoteUrl", "vivek");
		ReflectionTestUtils.setField(spyBean, "templatePath", "vivek");

		TemplateModel model = new TemplateModel();
		model.setArtifact(null);
		model.setProjectGroup("Dummy_grp");

		Assert.assertTrue(null == spyBean.pushLocalRepos(model));

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

	}
}