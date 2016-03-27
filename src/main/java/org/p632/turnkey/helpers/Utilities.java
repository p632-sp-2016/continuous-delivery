package org.p632.turnkey.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Utilities {
	
	public static String GetAllLines(File file)
	{
		String input = "";
		try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
			    StringBuffer fileContents = new StringBuffer();
			    input = reader.readLine();
			    while (reader.readLine() != null) {
			        fileContents.append(input);	        
					input += reader.readLine();					
			    }
			    
			    return input;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return input;
	}
	
}
