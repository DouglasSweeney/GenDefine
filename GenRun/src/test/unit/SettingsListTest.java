package test.unit;


import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.script.ScriptException;

import org.junit.Before;
import org.junit.Test;

import main.java.exceptions.IllegalParameterException;
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
	    settingsList = new SettingsList("scripts/test/onlyAFew.script"); 
		node = new SettingsNode("inputsdeductionstab", 123, 456);
		settingsList.add(node);
		
		node = settingsList.get("inputsdeductionstab");
	    
	    assertEquals(123, node.getX());
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testFileNotFoundException() throws FileNotFoundException {
	    settingsList = new SettingsList("scripts/test/onlyAFe.script"); 
	}
	
	@Test
	public void testSizeEqualsOne() throws FileNotFoundException {
	    settingsList = new SettingsList("scripts/test/onlyAFew.script"); 
		node = new SettingsNode("calculateButton", 123, 456);
		settingsList.add(node);
	    assertEquals(1, settingsList.size());
	}
	
	@Test
	public void testSize() throws FileNotFoundException {
	    settingsList = new SettingsList("scripts/test/onlyAFew.script"); 
		node = new SettingsNode("calculateButton", 123, 456);
		settingsList.add(node);
	    assertEquals(1, settingsList.size());
	}
	
	@Test
	public void testDoesntExist() throws FileNotFoundException, ScriptException, IOException {
		@SuppressWarnings("unused")
		String line;
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script");
	    
		node = new SettingsNode("calculateButton", 635, 346);
		settingsList.add(node);
		
		node = settingsList.get("calculateButto");
	    assertEquals(null, node);
	}
	
	@Test
	public void testAddTwoItems() throws FileNotFoundException, ScriptException, IOException {
		@SuppressWarnings("unused")
		String line;
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script");
	    
		node = new SettingsNode("calculateButton", 635, 346);
		settingsList.add(node);
		node = new SettingsNode("calculateButton2", 635, 346);
		settingsList.add(node);
		
		node = settingsList.get("calculateButton");
	    assertNotEquals(null, node);
	}
	
	@Test
	public void testReadln() throws FileNotFoundException, ScriptException, IOException {
		String line;
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script");
	    
	    line = settingsList.readLine();
	    
	    assertNotEquals(0, line.length());
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testReadlnFilePermissions() throws ScriptException, IOException {
		String line;
		
	    settingsList = new SettingsList("scripts/test/writeOnly.script");
	    
	    line = settingsList.readLine();
	    
	    assertEquals(0, line.length());
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testReadlnDirectory() throws ScriptException, IOException {
	    settingsList = new SettingsList("scripts/test");
	}
	
	@Test 
	public void testReadlnEndOfFile() throws ScriptException, IOException {
		String line;
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script");
	    
	    line = settingsList.readLine(); // valid
	    line = settingsList.readLine(); // valid
	    line = settingsList.readLine(); // valid
	    line = settingsList.readLine(); // valid
	    line = settingsList.readLine(); // invalid
	    
	    assertEquals(null, line);
	}
	
	@Test
	public void testCloseInvalid() throws FileNotFoundException, ScriptException, IOException {
		String line = "";
		
	    settingsList = new SettingsList("scripts/test/onlyAFew.script");
	    
		settingsList.close();
		settingsList.close();
	    
	    line = settingsList.readLine();
	    
	    assertEquals(0, line.length());
	}
}