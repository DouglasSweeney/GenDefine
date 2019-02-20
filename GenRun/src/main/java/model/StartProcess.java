package main.java.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StartProcess {
	String runCommand;
	
	JFrame frame;
    static Process process = null;
    static Process process2 = null;
		     
	public StartProcess(JFrame frame, String runCommand) {
		this.frame = frame;
		this.runCommand = runCommand;
	}
	
	public synchronized void start() throws InterruptedException, IllegalMonitorStateException, IOException {
	    ArrayList<String> list = new ArrayList<String>();
	    ProcessBuilder processBuilder = null;
	    StringTokenizer st = new StringTokenizer(runCommand);
	    while (st.hasMoreTokens()) {
	        list.add(st.nextToken());
	    }
	    
		if (process == null) {
		    try {
		    	processBuilder = new ProcessBuilder(list);
          
		    	processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
          processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
          processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
          
		      process = processBuilder.start();
		      try {
		        Thread.sleep(10000); // wait for window to activate - 10s
		      } catch (InterruptedException e) {
		        throw e;
		      } catch (IllegalMonitorStateException e) {
		        throw e;
		      }
		    } catch (IOException e) {
		    	throw e;
		    }   
		} 
		else 
	  if (process2 == null) {
        try {
          processBuilder = new ProcessBuilder(list);
          
          processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
          processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
          processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
          
          process2 = processBuilder.start();
          try {
            process2.waitFor();
            process2 = null;
          } catch (InterruptedException e) {
            throw e;
          } catch (IllegalMonitorStateException e) {
            throw e;
          }
        } catch (IOException e) {
          throw e;
        }   
    } 
    else {
		     JOptionPane.showMessageDialog(frame, "Can only start two (2) applications!");
		 }
	}
}
