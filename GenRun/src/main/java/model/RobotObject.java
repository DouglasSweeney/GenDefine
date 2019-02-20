package main.java.model;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.sourceforge.cobertura.CoverageIgnore;

public class RobotObject
{
  // Needed for cobertura (line coverage) to ignore the main() method
  public @interface CoberturaIgnore {};
	
  private int   autoDelay = 0;
  private boolean autoDelayForIdle2 = true;
  Robot robot = null;
     
  public RobotObject() {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      e.printStackTrace();
    }
   
  }

  public synchronized void keyPress(int keycode) {
	  robot.keyPress(keycode);
  }
  
  public synchronized void keyRelease(int keycode) {
	  robot.keyRelease(keycode);
  }
 
  public synchronized void mouseMove(int x, int y) {
	  robot.mouseMove(x, y);
  }
  
  public synchronized void mousePress(int button) {
	  button = InputEvent.getMaskForButton(button);
	  robot.mousePress(button);
  }
  
  public synchronized void mouseReleased(int button) {
	  button = InputEvent.getMaskForButton(button);
	  robot.mouseRelease(button);
  }
  
  public synchronized BufferedImage capture(Rectangle area) {   
	  BufferedImage img = robot.createScreenCapture(area);
	   
	  return img;
  }
  
  public synchronized void mouseWheel(int wheelAmount) {   
 	  robot.mouseWheel(wheelAmount);
  }
 
  public synchronized void delay(int timeDelay) {
    robot.delay(timeDelay);
  }
  
  public synchronized void autoDelayForIdle() {
    autoDelayForIdle2 = true;
	  robot.setAutoWaitForIdle(autoDelayForIdle2);
  }

  public synchronized void setAutoDelay(int ms) {
    autoDelay = 50;
	  robot.setAutoDelay(50);
  }

  @CoverageIgnore
  public static void main(String[] args) {
//    RobotObject robot = new RobotObject();
//    Rectangle area = new Rectangle(); 
//    String fileName = "actual/temp.png";
  
//    area.x = 192;
//    area.y = 256;
//    area.width = 384;
//    area.height = 346;
//    BufferedImage bufferedImage = robot.capture(area);    
//    File outputFile = new File(fileName);
//    try {
//      ImageIO.write(bufferedImage, "png", outputFile);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
  
    String fileName = "actual/temp.png";
    File outputFile = new File(fileName);
    try {
      Robot robot = new Robot();
      Toolkit toolkit = Toolkit.getDefaultToolkit();
      int height = (int)toolkit.getScreenSize().getHeight();
      int width = (int)toolkit.getScreenSize().getWidth();
      BufferedImage bi = robot.createScreenCapture(new Rectangle(1250, 150, 116, 118));
      ImageIO.write(bi, "png", outputFile);
    } catch (Exception ex) {
      ex.getMessage();
    }
  }
}
