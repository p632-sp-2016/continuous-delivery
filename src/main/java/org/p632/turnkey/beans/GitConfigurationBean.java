package org.p632.turnkey.beans;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitConfigurationBean {

	@Value("${user.authToken}")
	private String authtoken;

	@Value("${test.username}")
	private String username;

	@Value("${test.serverPath}")
	private String serverPath;

	@Value("${test.gitApi}")
	private String gitApi;

	@Value("${test.remoteUrl}")
	private String remoteUrl;

	@Value("${test.templatePath}")
	private String templatePath;

	private HttpEntity entity;
	private int statusCode;

	/**
	 * Creates remote repository on github.
	 * 
	 * @param remoteReposName
	 * @return
	 * @throws Exception
	 */
	public int createRemoteRepos(String remoteReposName) throws Exception {
		remoteReposName = (remoteReposName == null) ? "Default" : remoteReposName;
		try {

			HttpPost post = new HttpPost(gitApi);
			post.setHeader("Authorization", " token " + authtoken);
			HttpClient httpclient = new DefaultHttpClient();
			StringEntity params = new StringEntity("{\"name\": \"" + remoteReposName + "\"} ");
			post.setEntity(params);

			HttpResponse response = httpclient.execute(post);
			statusCode = response.getStatusLine().getStatusCode();

			entity = response.getEntity();
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				EntityUtils.consume(entity);
			} catch (IOException ex) {
				throw ex;
			}
		}
		return statusCode;
	}

	/**
	 * f Pushes the local server repository to remote
	 * 
	 * @param remoteReposName
	 * @throws Exception
	 */
	public Iterator<PushResult> pushLocalRepos(String remoteReposName) throws Exception {
		remoteReposName = (remoteReposName == null) ? "Default" : remoteReposName;

		serverPath = serverPath.replace(".", File.separator);
		templatePath = templatePath.replace(".", File.separator);
		File destTemplatePath = new File(serverPath + File.separator + "temp");
		File srcTemplatePath  = new File(templatePath);
		try {

			Thread.sleep(2000);

			destTemplatePath.mkdirs();
			
			String remoteRepoUrl = remoteUrl + "/" + username + "/" + remoteReposName + ".git";
			Git git = Git.init().setDirectory(destTemplatePath).call();

			Repository repository = FileRepositoryBuilder.create(new File(destTemplatePath.getAbsolutePath(), ".git"));
			FileUtils.copyDirectory(srcTemplatePath,new File(repository.getDirectory().getParent()));
		   
			git.add().addFilepattern(".").call();
			git.commit().setMessage("Auto Generated Template").call();

			CredentialsProvider crdn = new UsernamePasswordCredentialsProvider(authtoken, "");
			PushCommand pc = git.push();

			pc.setCredentialsProvider(crdn).setForce(true).setRemote(remoteRepoUrl).setPushAll();
			Iterator<PushResult> pushResult = pc.call().iterator();

			return pushResult;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally { 
			try {
				FileUtils.deleteDirectory(destTemplatePath);
			} catch (IOException ex) {
				throw ex;
			}
		}

	}

}
