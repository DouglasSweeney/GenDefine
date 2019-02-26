package main.java;

import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Collections;

import javax.script.ScriptException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.java.exceptions.IllegalParameterException;
import main.java.model.NodeCompare;
import main.java.model.SettingsList;
import main.java.model.StartProcess;
import main.java.ApplicationLogger;

public class Main extends JFrame implements WindowListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {
	private static final long serialVersionUID = 1L;

 	private KeyPressed keyPressed = null;
  /**
   * Set the logger property to its value.
   * 
   * @var Application_Logger LOGGER
   */
  private static final ApplicationLogger LOGGER = new ApplicationLogger();
	
//	private StartProcess startProcess = new StartProcess();
	private JLabel       currentLabel;
	private SettingsList settingsList = null;
	private int          lastX;
	private int          lastY;
	private String       item;
	private String       filename;
	private JButton      minimizeButton;
	private JComboBox<String> comboBox;
    private String       items[] = null;
    private boolean      changingTheList = false;
	private boolean      addingToList = false;
    
    private boolean      doingACaptureDefine = false;
    private boolean      doingTheUpperLeft = false;
    private Point        upperLeft = new Point();
    private boolean      doingTheLowerRight = false;
    private Point        lowerRight = new Point();
 
    private StartProcess startProcess;
    
	public Main(Options options) throws IOException {
        super();
        LOGGER.trace("Entering main() constructor");
        
 		startProcess = new StartProcess();
 		
		if (startProcess.run(options.getRunCommand(), options.getFileUnderTest()) == false) {
			JOptionPane.showMessageDialog(null, "Could not start the process (parameters?)");
			System.exit(1);
		}
		else {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
    	setTitle("GenDefine");
        setLayout(new FlowLayout());

    	addKeyListener(this);
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	addWindowListener(this);

    	// TODO Added for dos (dos defaults to location & size)
    	setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        minimizeButton = new JButton("Minimize");
        minimizeButton.addActionListener(this);
        add(minimizeButton);
 
        // TODO Remove 1 commented line
//		filename = options.getScriptDirectory() + File.separatorChar + options.getScriptName();
		filename = options.getScriptName();
		settingsList = new SettingsList(filename, options);
		try {
			settingsList.create();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			settingsList.build();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
        add(new JLabel("Select Current Define: "));
  
        items = getItems(settingsList.size());
        
		comboBox = new JComboBox<String>(items);
		comboBox.setFocusable(false);
		comboBox.addActionListener(this);
		add(comboBox);
		
        add(new JLabel("Current Define: "));
        
        currentLabel = new JLabel("");
        add(currentLabel);
//        if (settingsList.size() >= 1) {
//        	currentLabel.setText(settingsList.get(0).getLabel());	        	
//        }
		
        setFocusable(true);
    	
    	setUndecorated(true);
    	setOpacity(0.50f);
    	
    	pack();
    	// TODO: Remove 2 commented lines
//    	setLocation(options.getSutX(), options.getSutY());
//        setSize(options.getSutWidth(), options.getSutHeight());
    	setVisible(true);   	        
	}
	
	private String[] getItems(int size) {
		String items[];
    
    settingsList.sort();
    
		items = new String[size];
		for (int i=0; i<size; i++) {
			item = settingsList.get(i).getLabel();
			items[i] = item;
		}
		
		return items;
	}
	
	public void start(Options options) throws IOException, ScriptException {
		keyPressed =  new KeyPressed(this, settingsList);
		
		settingsList.write();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String  item;
		int     answer;
		
	    if (e.getSource() == minimizeButton) {	       	
        try {
          settingsList.write();
        } catch (IOException e1) {
          e1.printStackTrace();
        }

        setState(JFrame.ICONIFIED);
	    }
	    else
	    if (e.getSource() == comboBox) {
	    	if (changingTheList == false) {
	    		item = (String) comboBox.getSelectedItem();
	    		currentLabel.setText(item);
	    		answer = JOptionPane.showConfirmDialog(null, "Is this a capture area define?");
	    		if (answer == JOptionPane.YES_OPTION) {
	    			JOptionPane.showMessageDialog(null, "Click on the upper-left corner for the current capture.");
	    			doingACaptureDefine = true;
	    			doingTheUpperLeft = true;
	    			doingTheLowerRight = false;
	    		}
	    		else 
	        if (answer == JOptionPane.NO_OPTION) {
	    			JOptionPane.showMessageDialog(null, "Click on the X/Y coordinate for the current define.");
	    			doingACaptureDefine = false;
	    			doingTheUpperLeft = true;
	    			doingTheLowerRight = false;
	    		}
	    	}
	    	else
	    	if (addingToList == true) {
	    		addingToList = false;
	    	}
	    	else {
	    		changingTheList = false;
	    	}
	    }
	}
	
   @Override
   public void mouseDragged(MouseEvent e) {
   }

   @Override
   public void mouseMoved(MouseEvent e) {
     Point point;
     point = MouseInfo.getPointerInfo().getLocation();
     lastX = point.x;
     lastY = point.y;
     
   }
   
   @Override
   public void mouseClicked(MouseEvent e) {

	   if (doingACaptureDefine == true) {
		   if (doingTheUpperLeft == true) {
			   upperLeft.x = e.getXOnScreen();
			   upperLeft.y = e.getYOnScreen();
			   
         JOptionPane.showMessageDialog(null, "Click on the lower-right corner for the current capture.");
			   doingTheUpperLeft = false;
			   doingTheLowerRight = true;
		   }
		   else
		   if (doingTheLowerRight == true) {
	   			String target = currentLabel.getText();
	   	   		
	   			lowerRight.x = lastX;
	   			lowerRight.y = lastY;
	   			settingsList.set(target, upperLeft, lowerRight);
	   			
	   			changingTheList = true;
	   			comboBox.removeAllItems();
	   		 	items = getItems(settingsList.size());
	   			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(items);
	   			comboBox.setModel( model );
	   			
	   			try {
	   				settingsList.write();
	   			} catch (IOException e1) {
	   				e1.printStackTrace();
	   			}			   
		   }
				
	   }
	   else {
	       doingTheUpperLeft = false;
	       String target = currentLabel.getText();

	       settingsList.set(target, lastX, lastY);
   			  changingTheList = true;
   			  comboBox.removeAllItems();
   			  items = getItems(settingsList.size());
   			  DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(items);
   			  comboBox.setModel( model );
   			
   			  try {
   			    settingsList.write();
   			  } catch (IOException e1) {
   			    e1.printStackTrace();
   			  }
	     }
   }

   @Override
   public void mousePressed(MouseEvent e) {
   }

   @Override
   public void mouseReleased(MouseEvent e) {
   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }

   @Override
   public void keyTyped(KeyEvent e) {
   }

   @Override
   public void keyPressed(KeyEvent e) {
	   if (e != null) {
		   keyPressed.check(e.getKeyCode());
	   }
   }

   @Override
   public void keyReleased(KeyEvent e) {
	   keyPressed.setState(keyPressed.getNormalState());
   }
   
	/**
	 * @param currentLabel the currentLabel to set
	 */
	public void setComboBoxItem(String item) {
		this.comboBox.addItem(item);
	}

	/**
	 * @param currentLabel the currentLabel to set
	 */
	public void setTextCurrentLabel(String currentLabel) {
		this.currentLabel.setText(currentLabel);
	}

    /**
	 * @return the addingToList
	 */
	public boolean isAddingToList() {
		return addingToList;
	}

	/**
	 * @param addingToList the addingToList to set
	 */
	public void setAddingToList(boolean addingToList) {
		this.addingToList = addingToList;
	}
	
	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

 	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		startProcess.close();
		System.exit(2);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
  public static void main(String[] args) throws IOException, ScriptException {
    Options options = new Options(args);
    if (options.validate() == false) {
      throw new IllegalParameterException();
    }
    
      Main main = new Main(options);
      
      main.start(options);      
   }

}
