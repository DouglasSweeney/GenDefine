package main.java.model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.script.ScriptException;

import main.java.ApplicationLogger;
import main.java.Options;
import net.sourceforge.cobertura.CoverageIgnore;

public class SettingsList {
	// Needed for cobertura (line coverage) to ignore the main() & print() methods
	public @interface CoberturaIgnore {};
	
	private List<SettingsNode>       list = new ArrayList<SettingsNode>();
	private BufferedReader           bufferedReader = null;
	private boolean eof = false;
	private Options options;
	private String scriptPathAndName = "";
	
  /**
   * Set the logger property to its value.
   * 
   * @var Application_Logger LOGGER
   */
  private static final ApplicationLogger LOGGER = new ApplicationLogger();
  
 	public SettingsList(String scriptName, Options options) {
		this.scriptPathAndName = scriptName;
		this.options = options;
	}
	
	public boolean create() throws IOException {
		boolean valid = false;
	    File file = new File(scriptPathAndName);
	    
	    if (file.exists() && file.canRead()) {
	      valid = true;  
	      bufferedReader = new BufferedReader(new FileReader(scriptPathAndName));
	    }
	    else {
	    	valid = false;
	    }
	        
		return valid;
	}
	
	public String readLine() throws IOException, ScriptException {	
	    File file = new File(scriptPathAndName);
	    String line = "";
	    
	    if (bufferedReader != null && file.isFile() && file.canRead()) {
	    	try {
	    	  line = bufferedReader.readLine();
	    	  if (line != null) 
	    	    System.out.println("<" + line + ">");
	    	} catch (IOException e) {
	    	  throw e;
	    	}
	    }
	    else {
	    	throw new ScriptException("Can't read the file " + scriptPathAndName + " (permissions?).");
	    }
	    
	    return line;
	}
	
	public void close() {
   		try {
			if (bufferedReader != null) {
				bufferedReader.close();
				bufferedReader = null;
			}
        }
        catch (IOException e) {
    	    e.printStackTrace();
        }
	}
	
	private String getLabel(String line) {
		String label = "";
		String ch;
    int index = 0;		
		if (line.length() == 0) {
			return label;
		}
		
		ch = line.substring(index, index+1);
		while (line.length()-1 > index && ch.equals(" ") == false) {
			label = label.concat(new String(ch));
			index++;
			ch = line.substring(index, index+1);	
		}
		
		return label;
	}
	
  private String getNumberString(String line) {
    String label = "";
    int index = 0;
    String ch;
    
    if (line.length() == 0) {
      return label;
    }
    
    ch = line.substring(index, index+1);
    while (line.length()-1 > index && ch.equals(" ") == true) {
       index++;
       if (line.length()-1 < index)
         ch = line.substring(index, index+1);
    }
    
    ch = line.substring(index, index+1);
    while (line.length()-1 >= index && ch.equals(" ") == false) {
      label = label.concat(new String(ch));
      index++;
      if (line.length()-1  >= index) {
        ch = line.substring(index, index+1);
      }
    }
    
    return label;
  }
	public boolean build() throws IOException, ScriptException {
		boolean valid = true;
		String  line;
		String  label;
		String  x;
		String  y;
		String  LrX;
		String  LrY;
		
		SettingsNode node = null;
		
		while (eof == false) {
		    line = readLine();
		    if (line == null) {
		    	eof = true;
		    	continue;
		    }
		    line = line.trim();
		    if (line.startsWith("#") == false) {
		    	if (line.startsWith("define") == true) {
		    		line = line.replaceFirst("define", "");
		    	  line = line.trim();
		    	  //index = 0;
		        label = getLabel(line);
		        line = line.replaceFirst(label, "");
		        line = line.trim();
		        x = getNumberString(line);
		        line = line.replaceFirst(x, "");
		        line = line.trim();
		        if (line.matches("^[0-9 ]+$") == true) {     
              y = getNumberString(line);
              line = line.replaceFirst(y, "");
              line = line.trim();
		        }
		        else {
		          y = "-1";
		        }
		  	    node = new SettingsNode(label, x, y);
		  	    LrX = getNumberString(line);
		  	    line = line.replaceFirst(LrX, "");
		  	    line = line.trim();
		  	    LrY = getNumberString(line);
		  	    if (LrX.length() != 0 && LrY.length() != 0) {
		  	      Point lowerRight = new Point(-1, -1);
		  	      lowerRight.x = new Integer(LrX);
		  	      lowerRight.y = new Integer(LrY);
		  	      node.setLowerRight(lowerRight);
		  	    }
		  	    else {
              Point lowerRight = new Point(-1, -1);
              node.setLowerRight(lowerRight);
		    	  }
		    	  list.add(node);
		  
		    	}
			}
		}
    
    sort();
    
		return valid;
	}
  
	public void sort() {
     Collections.sort(list, new NodeCompare());
	}
	
	public void add(String define) {
		SettingsNode node = new SettingsNode(define);
		
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
	
	public SettingsNode get(String define) {
    SettingsNode node = null;
    boolean found = false;
    
    for (int i=0; i<list.size() && found == false; i++) {
      node = list.get(i);
      if (node.getLabel().equalsIgnoreCase(define)) 
        found = true;
    }

    if (found)
      return node;
    else
      return null;
  }
  
	public void set(String target, int x, int y) {
		SettingsNode node = null;
		boolean      found = false;
		
		for (int i=0; i<list.size() && found == false; i++) {
			node = get(i);
			if (node.getLabel().equals(target) == true) {
			  node.setX(x);
			  node.setY(y);
        node.setModified(true);
				found = true;
			}
		}
	}
	
	public void set(String target, Point upperLeft, Point lowerRight) {
		SettingsNode node = null;
		boolean      found = false;
		
		for (int i=0; i<list.size() && found == false; i++) {
			node = get(i);
			if (node.getLabel().equals(target) == true) {
			  node.setX(upperLeft.x);
			  node.setY(upperLeft.y);
				node.setModified(true);
				node.setLowerRight(lowerRight);
				
				found = true;
			}
		}
	}
	
	@CoverageIgnore
	public void print() {		
		SettingsNode node = null;
		
		for (int i=0; i<list.size(); i++) {
			node = list.get(i);
			System.out.println("Label: " + node.getLabel() + " X: " + node.getX() + " Y: " + node.getY() +
		                                                   " LrX: " + node.getLowerRightX() + 
		                                                   " LrY: " + node.getLowerRightY());
		}
	}
	
	private String getBaseName(String oldFileName) {
	    String baseName = "";		
		int    index = 0;
		String ch;
		
		ch = oldFileName.substring(index, index+1);
		while (oldFileName.length() > index && ch.equals(".") == false) {
			baseName = baseName.concat(new String(ch));
			index++;
			ch = oldFileName.substring(index, index+1);	
		}
		
		return baseName;
	}
	
	private void writeEntry(BufferedWriter bufferedWriter, SettingsNode node) throws IOException {
    String label;
    int width;
    int height;
    
    label = node.getLabel(); // May contain SettingsNode.WAS_MODIFIED
    if (node.getLowerRightX() == SettingsNode.INVALID_LOWER_RIGHT && 
        node.getLowerRightY() == SettingsNode.INVALID_LOWER_RIGHT) {
      label = label.replaceAll(SettingsNode.WAS_MODIFIED, "");
      if (node.getY() == SettingsNode.INVALID_LOWER_RIGHT)
        bufferedWriter.write("define " + label + " " + node.getX().toString());
      else
        bufferedWriter.write("define " + label + " " + node.getX().toString() + 
        " " + node.getY().toString());
    }
    else {
      if (label.contains(SettingsNode.WAS_MODIFIED)) {
        label = label.replaceAll(SettingsNode.WAS_MODIFIED, "");
        width = node.getLowerRightX() - node.getX();
        height = node.getLowerRightY() - node.getY();
      }
      else {
        width = node.getLowerRightX();
        height = node.getLowerRightY();        
      }
        bufferedWriter.write("define " + label + " " + node.getX().toString() + 
            " " + node.getY().toString()
            + " " + width + " " + height);
    }
    bufferedWriter.newLine();
	}
	
	private void writeNode(BufferedWriter bufferedWriter, SettingsNode node) throws IOException {
    String label;
    
    label = node.getLabel();
    if (label.contains(SettingsNode.WAS_MODIFIED)) {
      writeEntry(bufferedWriter, node);
    }
    else {
      writeEntry(bufferedWriter, node);
    }
	}
	
  public void write() throws IOException {		
		SettingsNode node = null;
		BufferedWriter bufferedWriter = null;
		String newFileName = "";	
		File file = new File(scriptPathAndName);
		
		newFileName = file.getName();
		if (options == null) {
       newFileName = "scripts/" + getBaseName(newFileName) + ".newScript";
		}
		else {
       newFileName = options.getScriptDirectory() + File.separatorChar + getBaseName(newFileName) + ".newScript";
		}
		
		File file2 = new File(newFileName);
		file2.createNewFile();
		bufferedWriter = new BufferedWriter(new FileWriter(file2));

    sort();
		for (int i=0; i<list.size(); i++) {
			node = list.get(i);
			writeNode(bufferedWriter, node);
		}
		
		bufferedWriter.close();
	}
	
	@CoverageIgnore
	public static void main(String[] args) throws IOException, ScriptException {
		SettingsList settingsList = new SettingsList("scripts/defines.script", null);
		
		try {
			settingsList.create();
		} catch (IOException e) {
			e.printStackTrace();
		}
		settingsList.build();
		settingsList.write();
		settingsList.print();
	}
}
