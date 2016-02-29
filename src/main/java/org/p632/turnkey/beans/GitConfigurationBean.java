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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class GitConfigurationBean {
	
	@Value("$user.authToken}")
	private String authtoken;

	@Value("${test.serverPath}")
	private String serverPath;
	
	@Value("${test.gitApi}")
	private String gitApi;
	
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

}
