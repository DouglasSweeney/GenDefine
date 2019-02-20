package test.unit;


import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.script.ScriptException;

import org.junit.Before;
import org.junit.Test;

import main.java.exceptions.IllegalParameterException;
import main.java.model.Script;
import main.java.model.SettingsNode;

public class ScriptTest {
	Script script = null;
	SettingsNode node = null;	
	String path;
	
	@Before
	public void Setup() {
	    script = null;
	    
	    path = "scripts/test/onlyAFew.script";
	} 
	
	@Test
	public void testHappyCase() throws FileNotFoundException {
		String line = "";
		
	    script = new Script("scripts/test/onlyAFew.script"); 
	    
	    while (script.eof() == false) {
	    	line = script.read();
	    }
	    assertEquals(null, line);
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testFileNotFoundException() throws FileNotFoundException {
	    script = new Script("scripts/test/onlyAFe.script"); 
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testReadlnFilePermissions() throws ScriptException, IOException {
	    script = new Script("scripts/test/writeOnly.script");
	    
	}
	@Test (expected=IllegalParameterException.class)
	public void testReadlnDirectory() throws ScriptException, IOException {
	    script = new Script("scripts/test");
	}
	
	@Test
	public void testCloseValid() throws FileNotFoundException, ScriptException, IOException {
	    script = new Script("scripts/test/onlyAFew.script");
	    
		script.close();
		script.close();
	}
}
