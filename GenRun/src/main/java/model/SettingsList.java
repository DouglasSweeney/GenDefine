package main.java.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.script.ScriptException;

import main.java.exceptions.IllegalParameterException;
import net.sourceforge.cobertura.CoverageIgnore;

public class SettingsList {
	// Needed for cobertura (line coverage) to ignore the main() method
	public @interface CoberturaIgnore {};

	private ArrayList<SettingsNode>  list = new ArrayList<SettingsNode>();
	private BufferedReader           bufferedReader = null;
	private String 					 scriptPathAndName = "";
	
	public SettingsList(String scriptName) throws FileNotFoundException {
        File file = new File(scriptName);

        if (file.exists() == false) {
          throw new IllegalParameterException("Script file does not exist.");
        }

        if (file.canRead() == false) {
        	throw new IllegalParameterException("Script file is not readable.");
        }
                
	    if (file.isDirectory() == true) {
	       	throw new IllegalParameterException("Script file is a directory.");
	    }
	        
		this.scriptPathAndName = scriptName;
		try {
	        bufferedReader = new BufferedReader(new FileReader(scriptPathAndName));
		} catch (FileNotFoundException e) {
			throw e;
		}
	}
		
	public String readLine() throws IOException, ScriptException {	
	    String line = "";
	    
	    if (bufferedReader != null) {
	    	try {
				line = bufferedReader.readLine();
			} catch (IOException e) {
				throw e;
			}
	    	if (line != null) 
				System.out.println("<" + line + ">");
	    }
	    
	    return line;
	}
	
	public void close() {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bufferedReader = null;
			}
	}
	
	public void add(SettingsNode node) {
	    list.add(node);
	}
	
	public int size() {
		return list.size();
	}
	
	public SettingsNode get(int i) {
		SettingsNode node = null;
		node = list.get(i);

		return node;
	}
	
	public SettingsNode get(String target) {
		SettingsNode node = null;
		boolean      found = false;
		
		for (int i=0; i<list.size() && found == false; i++) {
			node = get(i);
			if (node.getLabel().equals(target) == true) {
				found = true;
			}
		}
		
		if (found == false) {
			node = null;
		}
		
		return node;
	}
	
	
	@CoverageIgnore
	public void print() {		
		SettingsNode node = null;
		
		for (int i=0; i<list.size(); i++) {
			node = list.get(i);
			System.out.println("Label: " + node.getLabel() + " X: " + node.getX() + " Y: " + node.getY());
		}
	}
	
	@CoverageIgnore
	public static void main(String[] args) {
		SettingsNode node = null;
		
		SettingsList settingsList = null;
		try {
			settingsList = new SettingsList("/home/dks/software/demoRetirement/scripts/defines.script");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		settingsList.print();
	}
}
