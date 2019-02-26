package main.java;

import java.io.File;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import main.java.exceptions.IllegalParameterException;

public class Options {
	private String  runCommand = "";
	private String  scriptName = "";
	private String  fileUnderTest = "";
    private String  scriptDir = ".";
	private Integer x = 50;
	private String  xString;
	private Integer y = 50;
	private String  yString;
	private Integer width = 1300;
	private String  widthString;
	private Integer height = 800;
	private String  heightString;
	
	public Options(String[] argv) {
		 int c;
		 String arg;
		 LongOpt[] longopts = new LongOpt[8];
		 // 
		 StringBuffer sb = new StringBuffer();
		 longopts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
		 longopts[1] = new LongOpt("run", LongOpt.REQUIRED_ARGUMENT, sb, 'r'); 
		 longopts[2] = new LongOpt("scriptdir", LongOpt.REQUIRED_ARGUMENT, sb, 'd');  
		 longopts[3] = new LongOpt("sut", LongOpt.REQUIRED_ARGUMENT, sb, 's'); 
		 longopts[4] = new LongOpt("width", LongOpt.REQUIRED_ARGUMENT, sb, 'w'); 
		 longopts[5] = new LongOpt("height", LongOpt.REQUIRED_ARGUMENT, sb, 'e'); 
		 // 
		 Getopt g = new Getopt("GenDefine", argv, "hr:d:s:x:y:w:e:", longopts);
		 g.setOpterr(false); // We'll do our own error handling
		 //
		 while ((c = g.getopt()) != -1) {
	         switch (c) {
		        case 0:
			          arg = g.getOptarg();
			          if ((char)(new Integer(sb.toString())).intValue() == 'd') {
			        	  setScriptDirectory(arg);
			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 'r') {
			        	  setRunCommand(arg);
			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 'h') {
			        	  usage();
			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 's') {
			        	  setFileUnderTest(arg);
			          }			          
			          if ((char)(new Integer(sb.toString())).intValue() == 'x') {
			        	  xString = arg;
			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 'y') {
			        	  yString = arg;
			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 'w') {
			        	  widthString = arg;
			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 'e') {
			        	  heightString = arg;
			          }
			        break;
			          
			        case 'd':
		        	    setScriptDirectory(g.getOptarg());
		        	break;
			        
			        case 'r':
			        	setRunCommand(g.getOptarg());
			        break;
				     
			        case 'h':
				        usage();
				    break;
				    
			        case 's':
		        	    setFileUnderTest(g.getOptarg());
		        	break;
			        
			        case 'x':
			            xString = g.getOptarg();
			      	break;
					        
				    case 'y':
					    yString = g.getOptarg();
					break;
					    
				    case 'w':
				        widthString = g.getOptarg();
				    break;
					     
				    case 'e':
				        heightString = g.getOptarg();
				    break;
			     }
	 	}

		if (argv.length >=1) {
           setScript(argv[argv.length-1]);
		}
	}
	
	private void setFileUnderTest(String arg) {
		File file = null;
		
	    if (arg != null) {
	       fileUnderTest = arg;
	    }
	    
	    file = new File(fileUnderTest);
	    if (file.exists() == false) {
	    	throw new IllegalParameterException("File " + fileUnderTest + " does not exist.");
	    	
	    }
	}
		
	private void setRunCommand(String arg) {
       if (arg != null) {
      	   runCommand = arg;
        }
	}

	private void setScriptDirectory(String arg) {
		   scriptDir = arg;
		}
		

	private void setScript(String arg) {
	   scriptName = arg;
	}
	
	private void usage() {
		System.out.println("GenDefine");
		System.out.println("");
		System.out.println("-h, --help: prints this screen.");
		System.out.println("-r, --run: Command to execute the Software Under Test (SUT)"); 
		System.out.println("-d, --scriptdir: The directory where the scripts are located");
		System.out.println("-s, --sut: The Software Under Test"); 
		System.out.println("-x: The X coordinate of the top-left corner of the SUT. Default=50."); 
		System.out.println("-y: The Y coordinate of the top-left corner of the SUT. Default=50."); 
		System.out.println("-w, --width: The w)idth of the SUT graphic window. Default=1300."); 
		System.out.println("-e, --height: The he)ight of the SUT graphic window. Default=800."); 
		System.out.println("");
		System.out.println("Example:");
		System.out.println("	GenDefine -r \"java -jar\" -d scripts -s Retirement.jar -x 50 -y 50 -w 1300 -e 800 scripts/filename.script");
		System.out.println("");
	}

	private boolean validateScriptDir() {
		File dir = new File(getScriptDirectory());
		
		if (dir.exists() == false) {
			throw new IllegalParameterException("The script directory doesn't exist.");
		}
		
		if (dir.isDirectory() == false) {
			throw new IllegalParameterException("The script directory isn't a directory.");
		}
		
		if (dir.canRead() == false) {
			throw new IllegalParameterException("The script directory isn't readable.");			
		}
		
		return true;
	}
	
	private boolean validateSut() {
//		if (this.getFileUnderTest() == "")
//			return true;
		
		File file = new File(this.getFileUnderTest());
		
		if (file.exists() == false) {
			throw new IllegalParameterException("The SUT doesn't exist.");
		}
		
		if (file.isFile() == false) {
			throw new IllegalParameterException("The SUT isn't a file.");
		}
		
		if (file.canRead() == false) {
			throw new IllegalParameterException("The SUT isn't readable.");			
		}
		
		return true;
	}

	private boolean validateX() {

		if (xString == null)
			return true;
		
		try {
			x = new Integer(xString);
		}
		catch (Exception e){
			throw new IllegalParameterException("The x value is not a valid integer.");
		}
		
		return true;
		
	}

	private boolean validateY() {
		if (yString == null)
			return true;
		
		
		try {
			y = new Integer(yString);
		}
		catch (Exception e){
			throw new IllegalParameterException("The y value is not a valid integer.");
		}
		
		return true;
		
	}
	
	private boolean validateWidth() {
		if (widthString == null)
			return true;
		
		
		try {
			width = new Integer(widthString);
		}
		catch (Exception e){
			throw new IllegalParameterException("The width value is not a valid integer.");
		}
		
		return true;
		
	}

	private boolean validateHeight() {
		
		if (heightString == null)
			return true;
		
		try {
			height = new Integer(heightString);
		}
		catch (Exception e){
			throw new IllegalParameterException("The height value is not a valid integer.");
		}
		
		return true;
		
	}

	private boolean validateScriptName() {
//		if (this.getFileUnderTest() == "")
//			return true;
		
		File file = new File(this.getScriptName());
		
		if (file.exists() == false) {
			throw new IllegalParameterException("The script doesn't exist.");
		}
		
		if (file.isFile() == false) {
			throw new IllegalParameterException("The script isn't a file.");
		}
		
		if (file.canRead() == false) {
			throw new IllegalParameterException("The script isn't readable.");			
		}
		
		return true;
	}


    public boolean validate() {
     	if (validateScriptDir() == false)
    		return false;
     	
     	if (validateSut() == false)
    		return false;
     	
     	if (validateX() == false)
    		return false;
     	
     	if (validateY() == false)
    		return false;
     	
     	if (validateWidth() == false)
    		return false;
     	
     	if (validateHeight() == false)
    		return false;
     	
     	if (validateScriptName() == false)
     		return false;
     	
    	return true;
    }

	public String getRunCommand() {
    	return runCommand;
    }
    
    public String getScriptName() {
    	return scriptName;
    }
    
    public String getScriptDirectory() {
    	return scriptDir;
    }
    
    public String getFileUnderTest() {
    	return fileUnderTest;
    }
    
    public int getSutX() {
    	return x;
    }
    
    public int getSutY() {
    	return y;
    }
    
    public int getSutWidth() {
    	return width;
    }
    
    public int getSutHeight() {
    	return height;
    }   

   public static void main(String[] args) {
    	Options options = new Options(args);
    
        System.out.println("<" + options.getRunCommand() + " " + options.getFileUnderTest() + ">");
        System.out.println("<" + options.getScriptDirectory() + ">");
    }
}
