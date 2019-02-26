package test.unit;


import static org.junit.Assert.*;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.ScriptException;

import org.junit.Before;
import org.junit.Test;

import main.java.Options;
import main.java.model.SettingsList;
import main.java.model.SettingsNode;

public class SettingsListTest {
	SettingsList settingsList = null;
	SettingsNode node = null;	
	String path;
	
	@Before
	public void Setup() {
	    settingsList = null;
	    
	    path = "scripts/test/onlyAFew.script";
	} 
	
	@Test
	public void testHappyCase() throws FileNotFoundException {
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null); 
		settingsList.add("Test");
		
		node = settingsList.get(0);
	    
	    assertNotEquals(null, node);
	}
	
	@Test
	public void testFileFound() throws IOException {
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null);
	    assertEquals(true, settingsList.create());
	    
	}
	
	@Test
	public void testFileNotFound() throws IOException {
	    settingsList = new SettingsList("scripts/test/onlyAFe.script", null);
	    assertEquals(false, settingsList.create());
	    
	}
	
	@Test
	public void testSizeEqualsOne() throws FileNotFoundException {
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null); 
		settingsList.add("calculateButton");
	    assertEquals(1, settingsList.size());
	}
	
	@Test
	public void testNoCreateMethodCalled() throws FileNotFoundException, IOException {
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null);
	    
	    assertEquals(0, settingsList.size());
	}
	
	@Test
	public void testReadln() throws FileNotFoundException, ScriptException, IOException {
		String line;
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null);
	    settingsList.create();
	    
	    line = settingsList.readLine();
	    
	    assertNotEquals(0, line.length());
	}
	
	@Test (expected=ScriptException.class)
	public void testReadlnFilePermissions() throws ScriptException, IOException {
		String line;
		
	    settingsList = new SettingsList("scripts/test/writeOnly.script", null);
	    settingsList.create();
	    
	    line = settingsList.readLine();
	    
	    assertEquals(0, line.length());
	}

	@Test (expected=FileNotFoundException.class)
	public void testReadlnDirectory() throws ScriptException, IOException {
	    settingsList = new SettingsList("scripts/test", null);
	    settingsList.create();
	}

	@Test 
	public void testReadlnEndOfFile() throws ScriptException, IOException {
		String line;
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null);
	    settingsList.create();
	    
	    line = settingsList.readLine(); // valid
	    line = settingsList.readLine(); // valid
	    line = settingsList.readLine(); // valid
	    line = settingsList.readLine(); // valid
	    line = settingsList.readLine(); // invalid
	    
	    assertEquals(null, line);
	}
	
	@Test (expected=ScriptException.class)
	public void testCloseInvalid() throws FileNotFoundException, ScriptException, IOException {
		String line = "";
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null);
	    settingsList.create();
	    
		settingsList.close();
		settingsList.close();
	    
	    line = settingsList.readLine();
	    
	    assertEquals(0, line.length());
	}
	
	@Test
	public void testBuild() throws FileNotFoundException, ScriptException, IOException {
		boolean valid = false;
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null);
	    settingsList.create();
	    
		valid = settingsList.build();
	      
	    assertEquals(true, valid);
	}
	
	@Test
	public void testSet() throws FileNotFoundException, ScriptException, IOException {
		SettingsNode node = null;
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null);
	    settingsList.create();
	    
		settingsList.add("calculate");
	    settingsList.set("calculate", 1 ,2);
	    
	    node = settingsList.get(0);
	    
	    assertEquals("1", node.getX().toString());
	}
	
	@Test
	public void testInvalidSet() throws FileNotFoundException, ScriptException, IOException {		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null);
	    settingsList.create();
	    
//		settingsList.add("calculate");
	    settingsList.set("calculate", 1 ,2);
	    
	    assertEquals(0, settingsList.size());
	}
	
	@Test
	public void testSetCaptureArea() throws FileNotFoundException, ScriptException, IOException {
		SettingsNode node = null;
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null);
	    settingsList.create();
	    
		settingsList.add("calculate");
	    settingsList.set("calculate", new Point(1 ,2), new Point(3, 4));
	    
	    node = settingsList.get(0);
	    
	    assertEquals(3, node.getLowerRightX());
	}
	
	@Test
	public void testSetInvalidCaptureArea() throws FileNotFoundException, ScriptException, IOException {
	    settingsList = new SettingsList("scripts/test/onlyAFew.script", null);
	    settingsList.create();
	    
//		settingsList.add("calculate");
	    settingsList.set("calculate", new Point(1 ,2), new Point(3, 4));
	    
	    assertEquals(0, settingsList.size());
	}
	
	@Test
	public void testWriteOfCaptureArea() throws FileNotFoundException, ScriptException, IOException {
		String args[] = new String[3];
		
		args[0] = "-d";
		args[1] = "scripts/test";
		args[2] = "onlyAFew.script";
		Options options = new Options(args);
		
	    settingsList = new SettingsList(options.getScriptDirectory() + File.separatorChar + options.getScriptName(), options);
	    settingsList.create();
	    
		settingsList.add("calculate");
	    settingsList.set("calculate", new Point(1 ,2), new Point(3, 4));
	    
	    settingsList.write();
	    
	    String content = new String(Files.readAllBytes(Paths.get(options.getScriptDirectory() + 
	    		                    File.separatorChar + "onlyAFew.newScript")));
	    assertEquals(true, content.contains("define calculate 1 2 2 2"));
	}
	
	@Test
	public void testWriteOfMouseMove() throws FileNotFoundException, ScriptException, IOException {
		String args[] = new String[3];
		
		args[0] = "-d";
		args[1] = "scripts/test";
		args[2] = "onlyAFew.script";
		Options options = new Options(args);
		
	    settingsList = new SettingsList(options.getScriptDirectory() + File.separatorChar + options.getScriptName(), options);
	    settingsList.create();
	    
		settingsList.add("calculate");
	    settingsList.set("calculate", 1 ,2);
	    
	    settingsList.write();
	    
	    String content = new String(Files.readAllBytes(Paths.get(options.getScriptDirectory() + 
	    		                    File.separatorChar + "onlyAFew.newScript")));
	    assertEquals(true, content.contains("define calculate 1 2"));
	}
}