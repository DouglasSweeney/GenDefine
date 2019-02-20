package test.system;


import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import main.java.Options;
import main.java.model.Parse;
import main.java.model.RobotObject;
import main.java.model.Script;
import main.java.model.SettingsList;

public class ParseTest {
	Parse parse = null;
	
	String[] argv = new String[5];
	JFrame frame = null;
	Script script = null;
	Options options = null;
	SettingsList settingsList = null;
	RobotObject robot = null;
	
	@Before
	public void Setup() throws FileNotFoundException {
	    parse = null;
	    
	    frame = null;
	    script = null;
	    argv[0] = "-s";
	    argv[1] = "scripts";
	    argv[2] = "-c";
	    argv[3] = "capture/actual";
	    argv[4] = "main.script";
	    options = null;
	    settingsList = null;
	    robot = null;
	    
	    script = new Script("scripts/main.script");
	    
	    argv[0] = "-s";
	    argv[1] = "scripts";
	    argv[2] = "-c";
	    argv[3] = "capture/actual";
	    argv[4] = "main.script";
	    options = new Options(argv);
	    
	    settingsList = new SettingsList("scripts/test/onlyAFew.script");
	    robot = new RobotObject();
	} 
	
	@Test
	public void testHappyCase() {
		parse = new Parse(null, null, null, null, null);
//		parse = new Parse(frame, script, options, settingsList, robot);
		
		assertNotEquals(null, parse);
	}

	@Test
	public void testParse() throws NumberFormatException, FileNotFoundException, NullPointerException, IllegalMonitorStateException, IOException, InterruptedException {
		parse = new Parse(frame, script, options, settingsList, robot);
		parse.parse(script);
		assertNotEquals(null, parse);
	}

}
