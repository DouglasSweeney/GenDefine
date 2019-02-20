package main.java;


import java.io.File;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import main.java.exceptions.IllegalParameterException;

public class Options {
	private Integer xOffset = 0;
	private Integer yOffset = 0;
	private String  xOffsetString = "0";
	private String  yOffsetString = "0";
	private String  scriptDir = ".";
	private String  scriptName = "";
	private String  captureDir = ".";
	private String  definesFilename = "";
	
	public Options(String[] argv) {
		 int c;
		 String arg;
		 LongOpt[] longopts = new LongOpt[6];
		 // 
		 StringBuffer sb = new StringBuffer();
		 longopts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
		 longopts[1] = new LongOpt("capturedir", LongOpt.REQUIRED_ARGUMENT, sb, 'c'); 
		 longopts[2] = new LongOpt("scriptdir", LongOpt.REQUIRED_ARGUMENT, sb, 's'); 
		 longopts[3] = new LongOpt("xoffset", LongOpt.REQUIRED_ARGUMENT, sb, 'x'); 
		 longopts[4] = new LongOpt("yoffset", LongOpt.REQUIRED_ARGUMENT, sb, 'y');  
		 longopts[5] = new LongOpt("defines", LongOpt.REQUIRED_ARGUMENT, sb, 'd');  
		 // 
		 Getopt g = new Getopt("RunScript", argv, "-:c:h:s:x:y:d:", longopts);
		 g.setOpterr(false); // We'll do our own error handling
		 //
		 while ((c = g.getopt()) != -1) {
	         switch (c) {
		        case 0:
			          arg = g.getOptarg();
			          if ((char)(new Integer(sb.toString())).intValue() == 'h') {
			        	  usage();
			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 'c') {
				          captureDir = arg;			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 's') {
					      scriptDir = arg;			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 'x') {
					      xOffsetString = arg;
			          }			          
			          if ((char)(new Integer(sb.toString())).intValue() == 'y') {
			        	  yOffsetString = arg;
			          }
			          if ((char)(new Integer(sb.toString())).intValue() == 'd') {
					      definesFilename = arg;
			          }
			        break;
			          
		        case 'h':
			        usage();
			    break;
			    
			    case 'c':
		        	captureDir = g.getOptarg();
		        break;
			        
			    case 'x':
			        xOffsetString = g.getOptarg();
			    break;

			    case 'y':
			        yOffsetString = g.getOptarg();
			    break;
				     
			    case 's':
			        scriptDir = g.getOptarg();
			    break;
			    
			    case 'd':
			    	definesFilename = g.getOptarg();
			    break;
			
			    default:
			    break;
			 }
	    
	         if (argv.length >= 1) {
	        	 scriptName = argv[argv.length-1];
	         }
	 	}
	}
	
	private void usage() {
		System.out.println("GenRun");
		System.out.println("");
		System.out.println("-h, --help: prints this screen.");
		System.out.println("-c, --capturedir: dir to write graphic files in."); 
		System.out.println("-s, --scriptdir: dir to find script files in."); 
		System.out.println("-x, --xoffset: A X offset to add to X coordinates."); 
		System.out.println("-y, --yoffset: A Y offset to add to Y coordinates.");
		System.out.println("-d, --defines: The filename of all the defines.");
		System.out.println("");
		System.out.println("Example:");
		System.out.println("	GenRun -c capture/actual -s scripts -x 0 -y 0 -d defines.script main.script");
	}
	
	private boolean validateCaptureDir() {
		File dir = new File(getCaptureDir());
		
		if (dir.exists() == false) {
			throw new IllegalParameterException("The captureDir doesn't exist.");
		}
		
		if (dir.isDirectory() == false) {
			throw new IllegalParameterException("The captureDir isn't a directory.");
		}
		
		if (dir.canWrite() == false) {
			throw new IllegalParameterException("The captureDir isn't writable.");			
		}
		
		return true;
	}
	
	private boolean validateScriptDir() {
		File dir = new File(getScriptDir());
		
		if (dir.exists() == false) {
			throw new IllegalParameterException("The scriptDir doesn't exist.");
		}
		
		if (dir.isDirectory() == false) {
			throw new IllegalParameterException("The scriptDir isn't a directory.");
		}
		
		if (dir.canRead() == false) {
			throw new IllegalParameterException("The scriptDir isn't readable.");			
		}
		
		return true;
	}
	
	private boolean validateXOffset() {
		if (xOffsetString == null)
			return true;
		
	
		try {
			xOffset = new Integer(xOffsetString);
		}
		catch (Exception e){
			throw new IllegalParameterException("The xOffset is not a valid integer.");
		}
		
		return true;
	}
	
	private boolean validateYOffset() {
		if (yOffsetString == null)
			return true;
		
		try {
			yOffset = new Integer(yOffsetString);
		}
		catch (Exception e){
			throw new IllegalParameterException("The yOffset is not a valid integer.");
		}
		
		return true;
	}
	
	private boolean validateDefinesFilename() {
		if (definesFilename == "")
			return true;
		
		File file = new File(getScriptDir() + File.separatorChar + definesFilename);
		
		if (file.exists() == false) {
			throw new IllegalParameterException("The " + getScriptDir() + File.separatorChar + definesFilename + " doesn't exist.");
		}
		
		if (file.isFile() == false) {
			throw new IllegalParameterException("The " + getScriptDir() + File.separatorChar + definesFilename + " isn't a file.");
		}
		
		if (file.canRead() == false) {
			throw new IllegalParameterException("The " + getScriptDir() + File.separatorChar + definesFilename + " isn't readable.");			
		}
		
		return true;		
	}		
	
	private boolean validateScriptName() {
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
    	if (validateCaptureDir() == false) 
			return false;
    	
    	if (validateScriptDir() == false)
    		return false;
    	
    	if (validateXOffset() == false)
    		return false;
    	
    	if (validateYOffset() == false)
    		return false;
       	
    	if (validateDefinesFilename() == false)
    		return false;
    	
    	if (validateScriptName() == false)
    		return false;
    	
    	return true;
    }
    
	/**
	 * @return the xOffset
	 */
	public Integer getxOffset() {
		return xOffset;
	}

	/**
	 * @param xOffset the xOffset to set
	 */
	public void setxOffset(Integer xOffset) {
		this.xOffset = xOffset;
	}

	/**
	 * @return the yOffset
	 */
	public Integer getyOffset() {
		return yOffset;
	}

	/**
	 * @param yOffset the yOffset to set
	 */
	public void setyOffset(Integer yOffset) {
		this.yOffset = yOffset;
	}

	/**
	 * @return the scriptDir
	 */
	public String getScriptDir() {
		return scriptDir;
	}

	/**
	 * @param scriptDir the scriptDir to set
	 */
	public void setScriptDir(String scriptDir) {
		this.scriptDir = scriptDir;
	}

	/**
	 * @return the captureDir
	 */
	public String getCaptureDir() {
		return captureDir;
	}

	/**
	 * @return the scriptName
	 */
	public String getScriptName() {
		return scriptName;
	}

	/**
	 * @param captureDir the captureDir to set
	 */
	public void setCaptureDir(String captureDir) {
		this.captureDir = captureDir;
	}

	/**
	 * @param scriptName the scriptName to set
	 */
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	/**
	 * @return the xOffsetString
	 */
	public String getxOffsetString() {
		return xOffsetString;
	}

	/**
	 * @param xOffsetString the xOffsetString to set
	 */
	public void setxOffsetString(String xOffsetString) {
		this.xOffsetString = xOffsetString;
	}

	/**
	 * @return the yOffsetString
	 */
	public String getyOffsetString() {
		return yOffsetString;
	}

	/**
	 * @param yOffsetString the yOffsetString to set
	 */
	public void setyOffsetString(String yOffsetString) {
		this.yOffsetString = yOffsetString;
	}

	/**
	 * @return the definesFilename
	 */
	public String getDefinesFilename() {
		return definesFilename;
	}

	/**
	 * @param definesFilename the definesFilename to set
	 */
	public void setDefinesFilename(String definesFilename) {
		this.definesFilename = definesFilename;
	}

	public static void main(String[] args) {
    	Options options = new Options(args);
    
        System.out.println("definesFilename: " + options.getDefinesFilename());
        System.out.println("captureDir: " + options.getCaptureDir());
        System.out.println("scriptDir: " + options.getScriptDir());
        System.out.println("Last parameter: "+ args[args.length-1]);
        options.validate();
        options.setScriptName(args[args.length-1]);
    }
}
