package main.java.model;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import main.java.Options;
import main.java.exceptions.IllegalCommandException;
import main.java.exceptions.IllegalParameterException;

public class Parse {
	JFrame      frame;
	Script      script = null;
	Options     options = null;
	SettingsList settingsList = null;
	RobotObject robot  = null;
	String      captureFilename = "";
	StartProcess startProcess;
	
	public Parse(JFrame frame, Script script, Options options, SettingsList settingsList, RobotObject robot) {
	   this.frame = frame;
	   this.script = script;
	   this.options = options;
	   this.settingsList = settingsList;
	   this.robot = robot;
	}
	
	protected String getCommand(String line) {
		String   command = "";
		boolean  found;
		
		if (line.length() == 0) {
			return command;
		}
		
		found = false;
		for (int i=0; i<line.length() && found == false; i++) {
			if (line.charAt(i) == ' ') {
				found = true;
			}
			else {
				command = line.substring(0, i+1);
			}
			
		}
		
	  	return command;
	}

	private boolean getBooleanArg(String line) {
		int     i = 0;
		boolean found = false;
		boolean returnValue;
		String  answer = "";
		
		for (i=0; i<line.length() && found == false; i++) {
			if (line.charAt(i) == ' ') {
				found = true;
			} 
			else {
				answer = line.substring(0, i+1);
			}
		}
		
		if (answer.equals("true"))
			returnValue = true;
		else
			returnValue = false;
		
		return returnValue;
	}
	
	private int getEndOfSequence(int firstIndex, String line) {
		int     i = firstIndex;
		boolean found = false;
		
		while (found == false && i < line.length()) {
			if (line.charAt(i) == ' ') {
				found = true;
			} 
			else {
				i++;
			}
		}
		
		return i;
	}
	
	private int getIntArg(int set, String line) throws NumberFormatException {
		int     firstIndex;
		int     i = 0;
		int     number = 0;
		
		if (line.length() == 0) {
			return 0;
		}
		
		firstIndex = i;
		i = getEndOfSequence(firstIndex, line);
		try {
			number = new Integer(line.substring(firstIndex, i));
		}
		catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid number on command.");
		}
		
		if (set >= 2) {
			firstIndex = i+1;
			i = getEndOfSequence(firstIndex, line);
			try {
				number = new Integer(line.substring(firstIndex, i));
			}
			catch (NumberFormatException e) {
				throw new NumberFormatException("Invalid number on command.");
			}
		}
		
		if (set >= 3) {
			firstIndex = i+1;
			i = getEndOfSequence(firstIndex, line);
			try {
				number = new Integer(line.substring(firstIndex, i));
			}
			catch (NumberFormatException e) {
				throw new NumberFormatException("Invalid number on command.");
			}
		}
		
		if (set == 4) {
			firstIndex = i+1;
			i = getEndOfSequence(firstIndex, line);
			try {
				number = new Integer(line.substring(firstIndex, i));
			}
			catch (NumberFormatException e) {
				throw new NumberFormatException("Invalid number on command.");
			}
		}
		
		return number;
	}
	
	private synchronized void createScreenFile(Rectangle rect) throws IOException {
    String file = captureFilename;
    captureFilename = "";
    String fileName = options.getCaptureDir() + File.separatorChar + file + ".png";
    
    BufferedImage bufferedImage = robot.capture(rect);    
    File outputFile = new File(fileName);
     try {
      ImageIO.write(bufferedImage, "png", outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
     // TODO: Remove comment
/*
    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());    
    
    File f = new File(fileName);
    if(f.exists())
    f.delete();
    f.createNewFile();
    DataOutputStream os = new DataOutputStream(new FileOutputStream(f));
    BufferedImage img = robot.capture(screenRect);
    ImageIO.write(img, "jpg", os);
    os.close();
    */
	}	
	
	private String getStringArg(String command, String line) {
		String filename;
			
		line = line.replaceAll(command, "");
		filename = line.replaceAll(" ", "");
		
		return filename;
	}
	
	private String getStringFindFirstSpace(String line) {
		String string = "";
		int    index = 0;
		String ch;
		
		ch = line.substring(index, index+1);
		while (line.length() > index) {
		  if (ch.equals(" ") == true) {
		    break;
		  }
		  
			string = string.concat(new String(ch));
			index++;
			if (index < line.length()) {
			   ch = line.substring(index, index+1);
			}
		}
		
		return string;		
	}
	
	private String getStringRestOfLine(String line) {
		String string = "";
		
		string = line;
		
		return string;		
	}
	
	private synchronized void processLine(String line, String command) throws FileNotFoundException, NullPointerException, IOException,
                                                                 NumberFormatException, IllegalMonitorStateException, 
                                                                 InterruptedException {
		int       intArg0;
		int       intArg1;
		int       intArg2;
		int       intArg3;
		String    stringArg0;
		String    stringArg1;
		String    stringArg2;
		String    stringArg3;
		String    stringArg4;
		boolean   booleanArg0;
		Rectangle rect;
		SettingsNode node = null;
		
		line = line.substring(command.length());
		line = line.trim();
		
		if (command.equals("setautodelay")) {
			intArg0 = getIntArg(1, line);
			robot.setAutoDelay(intArg0);
		}
    else
    if (command.equals("delay")) {
      intArg0 = 0;
      node = settingsList.get(line);
      if (node != null) {
        intArg0 = node.getX();
      }
      else {
        if (line.matches("[0-9]+")) {
          intArg0 = getIntArg(1, line);
        }
      }
      robot.delay(intArg0);
    }
		else
		if (command.equals("mouseclicked")) {
			intArg0 = getIntArg(1, line);
            robot.mousePress(intArg0);
			robot.mouseReleased(intArg0);
		}
	    else
		if (command.equals("mousemoved")) {
			line = line.trim();
			if (line.matches("^[a-zA-Z][a-zA-Z0-9]*$") == true) {			
				stringArg0 = getStringRestOfLine(line); // label
				node = settingsList.get(stringArg0);
				robot.mouseMove(node.getX() + options.getxOffset(), node.getY() + options.getyOffset());
			}
			else {
				intArg0 = getIntArg(1, line) + new Integer(options.getxOffset());
				intArg1 = getIntArg(2, line) + new Integer(options.getyOffset());
				robot.mouseMove(intArg0 + options.getxOffset(), intArg1 + options.getyOffset());
			}
		}
	    else
		if (command.equals("keypressed")) {
			intArg0 = getIntArg(1, line);				
			robot.keyPress(intArg0);
		}
	    else
		if (command.equals("keyreleased")) {
			intArg0 = getIntArg(1, line);
			robot.keyRelease(intArg0);
		}
	    else
		if (command.equals("mousewheel")) {
			intArg0 = getIntArg(1, line);
			robot.mouseWheel(intArg0);
		}
	    else
		if (command.equals("capturefile")) {
			stringArg0 = getStringArg(command, line);
			captureFilename = stringArg0;
		}
		else
		if (command.equals("screencapture")) {
			if (line.matches("^[a-zA-Z][a-zA-Z0-9]+$") == true) {			
				stringArg0 = getStringRestOfLine(line); // label
				node = settingsList.get(stringArg0);
				line = node.getX() + " " + node.getY() + " " + node.getLowerRightX() + " " + node.getLowerRightY();
			}
			intArg0 = getIntArg(1, line);
			intArg1 = getIntArg(2, line);
			intArg2 = getIntArg(3, line);
			intArg3 = getIntArg(4, line);
			rect = new Rectangle(intArg0, intArg1, intArg2, intArg3);
			createScreenFile(rect);
		}
	    else
	    if (command.equals("autowaitforidle")) {
			booleanArg0 = getBooleanArg(line);
			if (booleanArg0 == true) {
				robot.autoDelayForIdle();
			}
		}
		else
		if (command.equals("run")) {
			String invokeCommand = line.substring(0, line.length());
			
	    	startProcess = new StartProcess(frame, invokeCommand);
	    	startProcess.start();
		}
		else
		if (command.equals("define")) {
			stringArg0 = getStringFindFirstSpace(line); // label
			line = line.replaceFirst(stringArg0, "");
			line = line.trim();
			
			stringArg1 = getStringFindFirstSpace(line); // X coordinate or upper-left X coordinate
			line = line.replaceFirst(stringArg1, "");
			line = line.trim();
			
      if (line.matches("^[0-9 ]+$") == true) {     
        stringArg2 = getStringFindFirstSpace(line);     // Y coordinate or upper-left Y coordinate
        line = line.replaceFirst(stringArg2, "");
        line = line.trim();
        if (line.matches("^[0-9 ]+$") == true) {     
          stringArg3 = getStringFindFirstSpace(line); // lower-right X coordinate
          line = line.replaceFirst(stringArg3, "");
          line = line.trim();
        
          stringArg4 = getStringRestOfLine(line); // lower-right Y coordinate
          line = line.replaceFirst(stringArg4, "");
          line = line.trim();
        
          node = new SettingsNode(stringArg0, new Integer(stringArg1), new Integer(stringArg2),
                  new Integer(stringArg3), new Integer(stringArg4));
          if (settingsList == null)
            throw new IllegalParameterException("\"define\" is not defined (need -d parameter?)");
          settingsList.add(node);
        }
        else {
          node = new SettingsNode(stringArg0, new Integer(stringArg1), new Integer(stringArg2));
          if (settingsList == null)
            throw new IllegalParameterException("\"define\" is not defined (need -d parameter?)");
          settingsList.add(node);            
        }
			}
      else {
        node = new SettingsNode(stringArg0, new Integer(stringArg1));
        if (settingsList == null)
          throw new IllegalParameterException("\"define\" is not defined (need -d parameter?)");
        settingsList.add(node);
      }
		}
		else 
		if (command.equals("source")) {
			// handled after this method
		}
		else {
			throw new IllegalCommandException("Command (" + command + ") is not a valid command.");
		}
	}
	
	public synchronized void parse(Script script) throws FileNotFoundException, NullPointerException, IOException, NumberFormatException, 
	                                        IllegalMonitorStateException, InterruptedException {
		String line;
	    String command;
	   
	    while (script.eof() == false) {
	    	line = script.read();
	    	if (script.eof() == false) {
	    		line = line.trim();
	    		System.out.println("<" + line + ">");    			
	    
				command = getCommand(line);
				command = command.toLowerCase();
	     
				if ((!line.isEmpty()) && (line.charAt(0) != '#')) {
					processLine(line, command);
	         
					if (command.equals("source")) {
						String filename  = line.replaceFirst(command, "");
						filename = filename.replaceAll(" ", "");
						filename = options.getScriptDir() + File.separatorChar + filename;
						Script scriptFile = new Script(filename);
	            
						parse(scriptFile);
					}
				}
	        }
	    }
	}
}
