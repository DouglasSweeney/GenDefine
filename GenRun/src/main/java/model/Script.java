package main.java.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import main.java.exceptions.IllegalParameterException;

public class Script {
	final char     newline = 10; 
	final String   commentChar = "#";
	
	boolean        eof = false;
	String         scriptName;
	FileReader     fileReader = null;
	BufferedReader bufferedReader = null;
	
	public Script(String scriptPathAndName) throws FileNotFoundException {
	    File file = new File(scriptPathAndName);
	      
	    if (file.exists() == false) {
        throw new IllegalParameterException("Script file does not exist (" + scriptPathAndName+ " ).");	      
	    }
	    
	    if (file.canRead() == false) {
	       	throw new IllegalParameterException("Script file is not readable.");
	    }
	        
	    if (file.isDirectory() == true) {
	       	throw new IllegalParameterException("Script file is a directory.");
	    }
	        
		this.scriptName = scriptPathAndName;
	    file = new File(scriptPathAndName);
		try {
			this.fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(this.fileReader);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("The file " + scriptPathAndName + " doesn't exist.");
		}
	}
	
	public String read() {
		String line = " ";
		try {
			line = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (line == null) {
			eof = true;
		}
		
		return line;
	}
	
	public boolean eof() {
	    return eof;	
	}
	
	public void close() throws IOException {
		if (fileReader != null) {
			try {
			    fileReader.close();
			} catch (IOException e) {
				throw e;
			}
			fileReader = null;
		}
 	}
}
