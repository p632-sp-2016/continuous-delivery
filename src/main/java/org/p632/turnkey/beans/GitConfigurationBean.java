package org.p632.turnkey.beans;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.revwalk.RevCommit;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.lib.Repository;


import java.io.IOException;
import java.io.UnsupportedEncodingException;


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
	
	private HttpEntity entity;
	private  int statusCode;
	public int createRemoteRepos(String remoteReposName)
	{
		remoteReposName = (remoteReposName == null) ? "Default" : remoteReposName;
		
    	try {
       
    	HttpPost post = new HttpPost(gitApi);
        
        post.setHeader("Authorization"," token "+authtoken);
        
        HttpClient httpclient = new DefaultHttpClient();
        
        StringEntity params =new StringEntity("{\"name\": \""+remoteReposName+"\"} ");
        post.setEntity(params);
        
        HttpResponse response = httpclient.execute(post);
      
        statusCode = response.getStatusLine().getStatusCode();

        entity = response.getEntity();
  }
    	catch( UnsupportedEncodingException ex )
    	{
    		//TODO exception handing 		
    		ex.printStackTrace();
    	}
    	catch( ClientProtocolException ex)
    	{
    		//TODO exception handing 		
    		ex.printStackTrace();
        }
    	catch( IOException ex)
    	{
    		//TODO exception handing 		
    		ex.printStackTrace();
    	}
    	
    	finally {
         	    try {
					EntityUtils.consume(entity);
				} catch (IOException ex) {
					
					//TODO exception handing 
					ex.printStackTrace();
				}
    	}
		return statusCode;
	}
	
	public void pushLocalRepos(String remoteReposName)
	{
		remoteReposName = (remoteReposName == null) ? "Default" : remoteReposName;
		File file = new File(serverPath+"\\new");
		try 
		{
		file.mkdirs();
		
		File localPath = File.createTempFile("Start-", "-End", file);
		Boolean status = localPath.delete();
        remoteUrl		= remoteUrl+"/"+username+"/"+remoteReposName+".git";
		Git git = Git.init().setDirectory(localPath).call();
        
        Repository repository = FileRepositoryBuilder.create(new File(localPath.getAbsolutePath(),".git"));
  
       	File myfile = new File(repository.getDirectory().getParent(), "testfile");
       	myfile.createNewFile();
        
       	DirCache index = git.add().addFilepattern("testfile").call();
		
       	RevCommit commit = git.commit().setMessage( "Create readme file" ).call();
		
		CredentialsProvider crdn = new UsernamePasswordCredentialsProvider(authtoken,"");
		
		PushCommand pc = git.push();
					
		PushCommand ah 	   = pc.setCredentialsProvider(crdn)
					     .setForce(true)
					     .setRemote(remoteUrl)
					     .setPushAll();
		

		
		Iterator<PushResult> it = pc.call().iterator();
                
		}
		catch(ConcurrentRefUpdateException ex ){
			
			ex.printStackTrace();

	 		//TODO additional action to be performed 
			}
		catch(AbortedByHookException ex)
		{
	 		//TODO additional action to be performed 

			ex.printStackTrace();
		}
		catch(NoHeadException ex)
		{	 //TODO additional action to be performed 

			ex.printStackTrace();
		}
		catch(NoFilepatternException ex)
		{   //TODO additional action to be performed 

			ex.printStackTrace();
		}
		catch(UnmergedPathsException ex)
		{   //TODO additional action to be performed 

			ex.printStackTrace();
		}
		catch(NoMessageException ex)
		{   //TODO additional action to be performed 

			ex.printStackTrace();
		}
		catch(WrongRepositoryStateException ex)
		{	//TODO additional action to be performed 

			ex.printStackTrace();
		}
		
		catch( GitAPIException ex){
			//TODO additional action to be performed 
			ex.printStackTrace();
				}
		catch(IOException ex)
		{	//TODO additional action to be performed 
			ex.printStackTrace();
		}
		catch( Exception ex){
			//TODO additional action to be performed 
			ex.printStackTrace();
			}
		finally{
			try {
				FileUtils.deleteDirectory(file);
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				}
		}


		
	}

}
