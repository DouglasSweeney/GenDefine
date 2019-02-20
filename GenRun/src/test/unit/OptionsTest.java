package test.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.Options;
import main.java.exceptions.IllegalParameterException;

public class OptionsTest {
	
	@Test (expected=IllegalParameterException.class)
	public void OptionNoParameters() {
		String args[] = new String[0];
		
//		args[0] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void OptionMainScriptParameters() {
		String args[] = new String[1];
		
		args[0] = "scripts/test/onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test
	public void OptionCCaptureDirectory() {
		String args[] = new String[3];
		
		args[0] = "-c";
		args[1] = "capture/actual";
		args[2] = "scripts/test/onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void InvalidOptionCCaptureDirectory() {
		String args[] = new String[3];
		
		args[0] = "-c";
		args[1] = "capture/actua";
		args[2] = "scripts/test/onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void OptionSScriptDirectory() {
		String args[] = new String[3];
		
		args[0] = "-s";
		args[1] = "scripts/test";
		args[2] = "scripts/test/onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void InvalidOptionSScriptDirectory() {
		String args[] = new String[3];
		
		args[0] = "-s";
		args[1] = "scripts/tes";
		args[2] = "scripts/test/onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void testOptionXOffset() {
		String args[] = new String[3];
		
		args[0] = "-x";
		args[1] = "0";
		args[2] = "scripts/test/onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testInvalidOptionXOffset() {
		String args[] = new String[3];
		
		args[0] = "-x";
		args[1] = "a";
		args[2] = "scripts/test/onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void testOptionYOffset() {
		String args[] = new String[3];
		
		args[0] = "-y";
		args[1] = "0";
		args[2] = "scripts/test/onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testInvalidOptionYOffset() {
		String args[] = new String[3];
		
		args[0] = "-y";
		args[1] = "a";
		args[2] = "scripts/test/onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void OptionDDefinesFile() {
		String args[] = new String[5];
		
		args[0] = "-s";
		args[1] = "scripts/test";
		args[2] = "-d";
		args[3] = "settings.script";
		args[4] = "scripts/test/onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
}
