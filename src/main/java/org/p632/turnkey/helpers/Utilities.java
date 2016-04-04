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
	
	public static String GetAllLines(String filepath)
	{
		String input = "";
		try {
				/*BufferedReader reader = new BufferedReader(new FileReader(file));
			    StringBuffer fileContents = new StringBuffer();
			    input = reader.readLine();
			    while (reader.readLine() != null) {
			        fileContents.append(input);	        
					input += reader.readLine();					
			    }
			    */
			    Path wiki_path = Paths.get(filepath);
	    	    Charset charset = Charset.forName("ISO-8859-1");
	    	  
	    	    List<String> lines = Files.readAllLines(wiki_path, charset);
	    	    //String xml = "";
	    	      for (String line : lines) {
	    	        input+=line;
	    	      }
			    return input;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return input;
	}
	
}
