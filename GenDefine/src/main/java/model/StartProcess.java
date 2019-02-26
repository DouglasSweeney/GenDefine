package main.java.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import main.java.Options;

public class StartProcess {
    static Process process = null;
		     
	public boolean run(String runCommand, String fileUnderTest) throws IOException {
	    Boolean valid = Boolean.valueOf(false);
	    String  invokeCommand = runCommand;
	    ArrayList<String> list = new ArrayList<String>();
	    ProcessBuilder processBuilder = null;
	    StringTokenizer st = new StringTokenizer(invokeCommand);
	    while (st.hasMoreTokens()) {
	        list.add(st.nextToken());
	    }
	    
		if (process == null) {
		    try {
		    	processBuilder = new ProcessBuilder(list);
//		    	processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
		    	processBuilder.redirectErrorStream(true);
		    	//		    	processBuilder.inheritIO();
		        process = processBuilder.start();
		        
//		        FocusTraversalPolicy.getDefaultComponent().requestFocus();	        
		        valid = Boolean.valueOf(true);
		    } catch (IOException e) {
		    	throw e;
		    }
		         
		} 
		else {
		     JOptionPane.showMessageDialog(null, "Can only start one (1) application under test!");
		 }
		      
		 return valid.booleanValue();
	}
   
    public void close() {
		if (process != null) {
		    process.destroy();
		    process = null;
		}
	}
    
    public static void main(String[] args) {
      StartProcess startProcess = new StartProcess();
      try {
        startProcess.run("java -Xms256m -Xmx2048m -cp . -jar Retirement.jar main.Main --scriptdir scripts scripts/main.script", "");
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
}
