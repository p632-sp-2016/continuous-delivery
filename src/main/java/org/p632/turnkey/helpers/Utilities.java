package org.p632.turnkey.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Utilities {
	
	private Utilities()
	{
		
	}
	
	public static String getAllLines(String filepath)
	{
		String input = "";
		try {
			    Path wiki_path = Paths.get(filepath);
	    	    Charset charset = Charset.forName("ISO-8859-1");
	    	  
	    	    List<String> lines = Files.readAllLines(wiki_path, charset);
	    	    
	    	      for (String line : lines) {
	    	        input+=line;
	    	      }
			    return input;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return input;
	}
	
}
