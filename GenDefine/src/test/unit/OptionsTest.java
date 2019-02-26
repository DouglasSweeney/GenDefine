package test.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.Options;
import main.java.exceptions.IllegalParameterException;

public class OptionsTest {
	
	@Test (expected=IllegalParameterException.class)
	public void testNoParametersOnCommandLine() {
		String args[] = new String[0];
		
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void testOptionDScriptsDirectory() {
		String args[] = new String[5];
		
		args[0] = "-d";
		args[1] = "scripts/test";
		args[2] = "-s";
		args[3] = "MyRetirement.jar";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test
	public void testOptionDCurrentDirectory() {
		String args[] = new String[3];
		
		args[0] = "-s";
		args[1] = "MyRetirement.jar";
		args[2] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testInvalidOptionDScriptsDirectory() {
		String args[] = new String[5];
		
		args[0] = "-d";
		args[1] = "scripts/tes";
		args[2] = "-s";
		args[3] = "MyRetirement.jar";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void testOptionSValidSut() {
		String args[] = new String[3];
		
		args[0] = "-s";
		args[1] = "MyRetirement.jar";
		args[2] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testInvalidOptionSInvalidSut() {
		String args[] = new String[3];
		
		args[0] = "-s";
		args[1] = "MyRetiremen.jar";
		args[2] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void testOptionXCoordinate() {
		String args[] = new String[5];
		
		args[0] = "-s";
		args[1] = "MyRetirement.jar";
		args[2] = "-x";
		args[3] = "0";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testInvalidOptionXCoordinate() {
		String args[] = new String[5];
		
		args[0] = "-s";
		args[1] = "MyRetirement.jar";
		args[2] = "-x";
		args[3] = "a";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void testOptionYCoordinate() {
		String args[] = new String[5];
		
		args[0] = "-s";
		args[1] = "MyRetirement.jar";
		args[2] = "-y";
		args[3] = "0";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testInvalidOptionYCoordinate() {
		String args[] = new String[5];
		
		args[0] = "-s";
		args[1] = "MyRetirement.jar";
		args[2] = "-y";
		args[3] = "a";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void testOptionWWidth() {
		String args[] = new String[5];
		
		args[0] = "-s";
		args[1] = "MyRetirement.jar";
		args[2] = "-w";
		args[3] = "0";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testInvalidOptionWWidth() {
		String args[] = new String[5];
		
		args[0] = "-s";
		args[1] = "MyRetirement.jar";
		args[2] = "-w";
		args[3] = "a";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void testOptionEHeight() {
		String args[] = new String[5];
		
		args[0] = "-s";
		args[1] = "MyRetirement.jar";
		args[2] = "-e";
		args[3] = "0";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testInvalidOptionEHeight() {
		String args[] = new String[5];
		
		args[0] = "-s";
		args[1] = "MyRetirement.jar";
		args[2] = "-e";
		args[3] = "a";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
	
	@Test
	public void testLongOptionDScriptsDirectory() {
		String args[] = new String[5];
		
		args[0] = "--scriptdir";
		args[1] = "scripts/test";
		args[2] = "--sut";
		args[3] = "MyRetirement.jar";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());
	}
	
	@Test
	public void testLongOptionValidSut() {
		String args[] = new String[3];
		
		args[0] = "--sut";
		args[1] = "MyRetirement.jar";
		args[2] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test
	public void testLongOptionWidth() {
		String args[] = new String[5];
		
		args[0] = "--width";
		args[1] = "0";
		args[2] = "-s";
		args[3] = "MyRetirement.jar";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());
	}
	
	@Test
	public void testLongOptionHeight() {
		String args[] = new String[5];
		
		args[0] = "--height";
		args[1] = "0";
		args[2] = "--sut";
		args[3] = "MyRetirement.jar";
		args[4] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(true, options.validate());		
	}
	
	@Test (expected=IllegalParameterException.class)
	public void testLongOptionHeightNoParameter() {
		String args[] = new String[4];
		
		args[0] = "--sut";
		args[1] = "MyRetirement.jar";
		args[2] = "--height";
		args[3] = "onlyAFew.script";
		Options options = new Options(args);
		
	    assertEquals(false, options.validate());		
	}
}
