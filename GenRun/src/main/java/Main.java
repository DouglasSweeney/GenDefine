package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import main.java.exceptions.IllegalCommandException;
import main.java.exceptions.IllegalParameterException;
import main.java.model.Parse;
import main.java.model.RobotObject;
import main.java.model.Script;
import main.java.model.SettingsList;
import main.java.model.StartProcess;

public class Main extends JFrame {
  private static final long serialVersionUID = 1L;

  JFrame frame = this;
	StartProcess startProcess = null;
	Options options = null;
	SettingsList settingsList = null;
	Script script = null;
	RobotObject robot = new RobotObject();
	
	private Main(Options options) {
		this.options = options;
	}    	
	
	public void startRunScript(Options options) throws FileNotFoundException, NullPointerException, 
	                                                   IOException, NumberFormatException, IllegalMonitorStateException,
	                                                   InterruptedException {
		String filename;
 		
		script = new Script(options.getScriptName());    			
		
		filename = options.getScriptDir() + File.separatorChar + options.getDefinesFilename();
		if (options.getDefinesFilename() != null) {
		   settingsList = new SettingsList(filename);
		}
		
		Parse parse = new Parse(frame, script, options, settingsList, robot);	
		parse.parse(script);
	}

    public static void main(String[] args) {
		Options options = new Options(args);		
		if (options.validate() == false) {
			throw new IllegalParameterException();
		}
		
		try {
			Main main = new Main(options);
			main.startRunScript(options);
		}
		catch (IllegalCommandException e) {
			e.printStackTrace();
        }
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		catch (IllegalMonitorStateException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
     }
}
